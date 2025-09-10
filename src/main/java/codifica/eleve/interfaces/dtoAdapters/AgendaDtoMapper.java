package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AgendaDtoMapper {

    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;
    private final PetDtoMapper petDtoMapper;
    private final ClienteDtoMapper clienteDtoMapper;
    private final RacaDtoMapper racaDtoMapper;


    public AgendaDtoMapper(PetRepository petRepository, ServicoRepository servicoRepository, PetDtoMapper petDtoMapper, ClienteDtoMapper clienteDtoMapper, RacaDtoMapper racaDtoMapper) {
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
        this.petDtoMapper = petDtoMapper;
        this.clienteDtoMapper = clienteDtoMapper;
        this.racaDtoMapper = racaDtoMapper;
    }

    public Agenda toDomain(AgendaDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new NotFoundException("Pet não encontrado."));

        List<Servico> servicos = dto.getServicos().stream()
                .map(servicoDTO -> {
                    Servico servico = servicoRepository.findById(servicoDTO.getId())
                            .orElseThrow(() -> new NotFoundException("Serviço não encontrado."));
                    servico.setValor(new ValorMonetario(servicoDTO.getValor()));
                    return servico;
                })
                .collect(Collectors.toList());

        ValorMonetario valorDeslocamento = new ValorMonetario(
                Optional.ofNullable(dto.getValorDeslocamento()).orElse(BigDecimal.ZERO)
        );
        Periodo periodo = new Periodo(dto.getDataHoraInicio(), dto.getDataHoraFim());

        return new Agenda(pet, servicos, valorDeslocamento, periodo);
    }

    public AgendaDTO toDto(Agenda domain) {
        AgendaDTO dto = new AgendaDTO();
        if (domain.getId() != null) {
            dto.setId(domain.getId().getValue());
        }

        Pet petDomain = domain.getPet();
        PetDTO petDTO = new PetDTO();
        petDTO.setId(petDomain.getId().getValue());
        petDTO.setNome(petDomain.getNome());
        RacaDTO racaDTO = racaDtoMapper.toDto(petDomain.getRaca());
        petDTO.setRaca(racaDTO);
        dto.setPet(petDTO);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(petDomain.getCliente().getId().getValue());
        clienteDTO.setNome(petDomain.getCliente().getNome());
        dto.setCliente(clienteDTO);

        dto.setServicos(domain.getServicos().stream()
                .map(servico -> {
                    ServicoDTO servicoDTO = new ServicoDTO();
                    servicoDTO.setId(servico.getId().getValue());
                    servicoDTO.setNome(servico.getNome());
                    servicoDTO.setValor(servico.getValor().getValor());
                    return servicoDTO;
                })
                .collect(Collectors.toList()));
        dto.setValorDeslocamento(domain.getValorDeslocamento().getValor());
        dto.setDataHoraInicio(domain.getPeriodo().getInicio());
        dto.setDataHoraFim(domain.getPeriodo().getFim());

        BigDecimal valorTotal = BigDecimal.ZERO;
        if (dto.getServicos() != null) {
            valorTotal = dto.getServicos().stream()
                    .map(ServicoDTO::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        if (dto.getValorDeslocamento() != null) {
            valorTotal = valorTotal.add(dto.getValorDeslocamento());
        }
        dto.setValorTotal(valorTotal);
        return dto;
    }
}
