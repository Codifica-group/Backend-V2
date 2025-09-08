package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.AgendaDTO;
import codifica.eleve.interfaces.dto.ServicoDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AgendaDtoMapper {

    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;

    public AgendaDtoMapper(PetRepository petRepository, ServicoRepository servicoRepository) {
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
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
        dto.setPetId(domain.getPet().getId().getValue());
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
        return dto;
    }
}
