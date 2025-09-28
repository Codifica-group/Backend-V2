package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.interfaces.dto.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SolicitacaoAgendaDtoMapper {

    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;
    private final RacaDtoMapper racaDtoMapper;

    public SolicitacaoAgendaDtoMapper(PetRepository petRepository, ServicoRepository servicoRepository, RacaDtoMapper racaDtoMapper) {
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
        this.racaDtoMapper = racaDtoMapper;
    }

    public SolicitacaoAgenda toDomain(SolicitacaoAgendaDTO dto) {
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

        return new SolicitacaoAgenda(dto.getChatId(), pet, servicos, valorDeslocamento, dto.getDataHoraInicio(), dto.getDataHoraFim(), dto.getDataHoraSolicitacao(), dto.getStatus());
    }

    public SolicitacaoAgendaDTO toDto(SolicitacaoAgenda domain) {
        SolicitacaoAgendaDTO dto = new SolicitacaoAgendaDTO();
        if (domain.getId() != null) {
            dto.setId(domain.getId().getValue());
        }
        dto.setChatId(domain.getChatId());

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
        dto.setDataHoraInicio(domain.getDataHoraInicio());
        dto.setDataHoraFim(domain.getDataHoraFim());
        dto.setDataHoraSolicitacao(domain.getDataHoraSolicitacao());
        dto.setStatus(domain.getStatus());

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
