package codifica.eleve.infrastructure.adapters;

import codifica.eleve.domain.cliente.Cliente;
import codifica.eleve.domain.pet.Pet;
import codifica.eleve.domain.raca.Raca;
import codifica.eleve.infrastructure.persistence.cliente.ClienteEntity;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetEntity toEntity(Pet domain) {
        if (domain == null) return null;

        PetEntity entity = new PetEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());

        if (domain.getCliente() != null) {
            ClienteEntity clienteEntity = new ClienteEntity();
            clienteEntity.setId(domain.getCliente().getId());
            entity.setCliente(clienteEntity);
        }

        if (domain.getRaca() != null) {
            RacaEntity racaEntity = new RacaEntity();
            racaEntity.setId(domain.getRaca().getId());
            entity.setRaca(racaEntity);
        }
        return entity;
    }


    public Pet toDomain(PetEntity entity) {
        if (entity == null) return null;

        Pet domain = new Pet();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());

        if (entity.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(entity.getCliente().getId());
            cliente.setNome(entity.getCliente().getNome());
            domain.setCliente(cliente);
        }

        if (entity.getRaca() != null) {
            Raca raca = new Raca();
            raca.setId(entity.getRaca().getId());
            raca.setNome(entity.getRaca().getNome());
            domain.setRaca(raca);
        }
        return domain;
    }
}
