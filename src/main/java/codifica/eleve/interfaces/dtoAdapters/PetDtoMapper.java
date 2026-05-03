package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.raca.porte.PorteRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.ClienteDTO;
import codifica.eleve.interfaces.dto.PetDTO;
import codifica.eleve.interfaces.dto.PorteDTO;
import codifica.eleve.interfaces.dto.RacaDTO;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper {

    private final ClienteRepository clienteRepository;
    private final RacaRepository racaRepository;
    private final PorteRepository porteRepository;

    public PetDtoMapper(ClienteRepository clienteRepository, RacaRepository racaRepository, PorteRepository porteRepository) {
        this.clienteRepository = clienteRepository;
        this.racaRepository = racaRepository;
        this.porteRepository = porteRepository;
    }

    public Pet toDomain(PetDTO dto) {
        if (dto == null) {
            return null;
        }

        validarCampoObrigatorio("nome", dto.getNome());
        validarCampoObrigatorio("sexo", dto.getSexo());
        validarCampoObrigatorio("foto", dto.getFoto());

        if (dto.getClienteId() == null) {
            throw new IllegalArgumentException("Campo obrigatório ausente: clienteId.");
        }

        if (dto.getRacaId() == null) {
            throw new IllegalArgumentException("Campo obrigatório ausente: racaId.");
        }

        if (dto.getPorteId() == null) {
            throw new IllegalArgumentException("Campo obrigatório ausente: porteId.");
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));

        Raca raca = racaRepository.findById(dto.getRacaId())
                .orElseThrow(() -> new NotFoundException("Raça não encontrada."));

        Pet domain = new Pet(dto.getNome(), raca, cliente);
        domain.setSexo(dto.getSexo().trim());
        domain.setFoto(dto.getFoto().trim());
        if (dto.getId() != null) {
            domain.setId(new Id(dto.getId()));
        }

        Porte porte = porteRepository.findById(dto.getPorteId())
                .orElseThrow(() -> new NotFoundException("Porte não encontrado."));
        domain.setPorte(porte);
        return domain;
    }

    public PetDTO toDto(Pet domain) {
        PetDTO dto = new PetDTO();
        dto.setId(domain.getId().getValue());
        dto.setNome(domain.getNome());
        dto.setSexo(domain.getSexo());
        dto.setFoto(domain.getFoto());

        if (domain.getCliente() != null) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(domain.getCliente().getId().getValue());
            clienteDTO.setNome(domain.getCliente().getNome());
            dto.setCliente(clienteDTO);
            dto.setClienteId(clienteDTO.getId());
        }

        if (domain.getRaca() != null) {
            RacaDTO racaDTO = new RacaDTO();
            racaDTO.setId(domain.getRaca().getId().getValue());
            racaDTO.setNome(domain.getRaca().getNome());
            if (domain.getRaca().getPorte() != null) {
                racaDTO.setPorteId(domain.getRaca().getPorte().getId().getValue());
                racaDTO.setPorteNome(domain.getRaca().getPorte().getNome());
            }
            dto.setRaca(racaDTO);
            dto.setRacaId(racaDTO.getId());
        }

        Porte porte = domain.getPorte();
        if (porte == null && domain.getRaca() != null) {
            porte = domain.getRaca().getPorte();
        }

        if (porte != null) {
            PorteDTO porteDTO = new PorteDTO();
            porteDTO.setId(porte.getId().getValue());
            porteDTO.setNome(porte.getNome());
            dto.setPorte(porteDTO);
            dto.setPorteId(porteDTO.getId());
        }
        return dto;
    }

    public PetDTO toChatbotDto(Pet domain) {
        return toDto(domain);
    }

    private void validarCampoObrigatorio(String nomeCampo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório ausente: " + nomeCampo + ".");
        }
    }
}
