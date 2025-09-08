package codifica.eleve.interfaces.dtoAdapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.ClienteDTO;
import codifica.eleve.interfaces.dto.PetDTO;
import codifica.eleve.interfaces.dto.RacaDTO;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper {

    private final ClienteRepository clienteRepository;
    private final RacaRepository racaRepository;

    public PetDtoMapper(ClienteRepository clienteRepository, RacaRepository racaRepository) {
        this.clienteRepository = clienteRepository;
        this.racaRepository = racaRepository;
    }

    public Pet toDomain(PetDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));

        Raca raca = racaRepository.findById(dto.getRacaId())
                .orElseThrow(() -> new NotFoundException("Raça não encontrada."));

        Pet domain = new Pet(dto.getNome(), raca, cliente);
        if (dto.getId() != null) {
            domain.setId(new Id(dto.getId()));
        }
        return domain;
    }

    public PetDTO toDto(Pet domain) {
        PetDTO dto = new PetDTO();
        dto.setId(domain.getId().getValue());
        dto.setNome(domain.getNome());

        if (domain.getCliente() != null) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(domain.getCliente().getId().getValue());
            clienteDTO.setNome(domain.getCliente().getNome());
            dto.setCliente(clienteDTO);
        }

        if (domain.getRaca() != null) {
            RacaDTO racaDTO = new RacaDTO();
            racaDTO.setId(domain.getRaca().getId().getValue());
            racaDTO.setNome(domain.getRaca().getNome());
            dto.setRaca(racaDTO);
        }
        return dto;
    }
}
