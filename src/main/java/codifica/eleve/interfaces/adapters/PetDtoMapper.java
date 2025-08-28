package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.interfaces.dto.PetDTO;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper {
    public Pet toDomain(PetDTO dto) {
        Pet domain = new Pet();
        domain.setId(dto.getId());
        domain.setNome(dto.getNome());

        if (dto.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getClienteId());
            domain.setCliente(cliente);
        }

        if (dto.getRacaId() != null) {
            Raca raca = new Raca();
            raca.setId(dto.getRacaId());
            domain.setRaca(raca);
        }
        return domain;
    }


    public PetDTO toDto(Pet domain) {
        PetDTO dto = new PetDTO();
        dto.setId(domain.getId());
        dto.setNome(domain.getNome());

        if (domain.getCliente() != null) {
            dto.setClienteId(domain.getCliente().getId());
            PetDTO.ClienteDTO clienteDTO = new PetDTO.ClienteDTO();
            clienteDTO.setId(domain.getCliente().getId());
            clienteDTO.setNome(domain.getCliente().getNome());
            dto.setCliente(clienteDTO);
        }

        if (domain.getRaca() != null) {
            dto.setRacaId(domain.getRaca().getId());
            PetDTO.RacaDTO racaDTO = new PetDTO.RacaDTO();
            racaDTO.setId(domain.getRaca().getId());
            racaDTO.setNome(domain.getRaca().getNome());
            dto.setRaca(racaDTO);
        }
        return dto;
    }
}
