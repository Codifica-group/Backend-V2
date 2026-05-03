package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.infrastructure.persistence.cliente.ClienteEntity;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    private final RacaMapper racaMapper;

    public PetMapper(RacaMapper racaMapper) {
        this.racaMapper = racaMapper;
    }

    public PetEntity toEntity(Pet domain) {
        if (domain == null) return null;

        PetEntity entity = new PetEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }

        entity.setNome(domain.getNome());
        entity.setSexo(domain.getSexo());
        entity.setFoto(domain.getFoto());

        if (domain.getCliente() != null) {
            ClienteEntity clienteEntity = new ClienteEntity();
            clienteEntity.setId(domain.getCliente().getId().getValue());
            entity.setCliente(clienteEntity);
        }

        if (domain.getRaca() != null) {
            RacaEntity racaEntity = new RacaEntity();
            racaEntity.setId(domain.getRaca().getId().getValue());
            entity.setRaca(racaEntity);
        }

        if (domain.getPorte() != null) {
            PorteEntity porteEntity = new PorteEntity();
            porteEntity.setId(domain.getPorte().getId().getValue());
            entity.setPorte(porteEntity);
        }
        return entity;
    }

    public Pet toDomain(PetEntity entity) {
        if (entity == null) return null;

        Pet domain = new Pet();
        domain.setId(new Id(entity.getId()));
        domain.setNome(entity.getNome());
        domain.setSexo(entity.getSexo());
        domain.setFoto(entity.getFoto());

        if (entity.getCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(new Id(entity.getCliente().getId()));
            cliente.setNome(entity.getCliente().getNome());
            domain.setCliente(cliente);
        }

        if (entity.getRaca() != null) {
            Raca raca = racaMapper.toDomain(entity.getRaca());
            domain.setRaca(raca);
        }

        if (entity.getPorte() != null) {
            Porte porte = new Porte();
            porte.setId(new Id(entity.getPorte().getId()));
            porte.setNome(entity.getPorte().getNome());
            domain.setPorte(porte);
        }
        return domain;
    }
}
