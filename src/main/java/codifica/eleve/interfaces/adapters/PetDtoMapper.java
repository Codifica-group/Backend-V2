package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.interfaces.dto.ClienteDTO;
import codifica.eleve.interfaces.dto.PetDTO;
import codifica.eleve.interfaces.dto.RacaDTO;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper {
    public Pet toDomain(PetDTO dto) {
        Pet domain = new Pet();
        if (dto.getId() != null) {
            domain.setId(new Id(dto.getId()));
        }
        domain.setNome(dto.getNome());

        if (dto.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(new Id(dto.getCliente().getId()));
            domain.setCliente(cliente);
        }

        if (dto.getRaca() != null) {
            Raca raca = new Raca();
            raca.setId(new Id(dto.getRaca().getId()));
            domain.setRaca(raca);
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
