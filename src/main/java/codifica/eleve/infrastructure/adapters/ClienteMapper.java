package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Telefone;
import codifica.eleve.infrastructure.persistence.cliente.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteEntity entity = new ClienteEntity();
        if (cliente.getId() != null) {
            entity.setId(cliente.getId().getValue());
        }
        entity.setNome(cliente.getNome());
        if (cliente.getTelefone() != null) {
            entity.setTelefone(cliente.getTelefone().getNumero());
        }
        if (cliente.getEndereco() != null) {
            entity.setCep(cliente.getEndereco().getCep());
            entity.setRua(cliente.getEndereco().getRua());
            entity.setNumEndereco(cliente.getEndereco().getNumEndereco());
            entity.setBairro(cliente.getEndereco().getBairro());
            entity.setCidade(cliente.getEndereco().getCidade());
            entity.setComplemento(cliente.getEndereco().getComplemento());
        }
        return entity;
    }

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        Telefone telefone = new Telefone(entity.getTelefone());
        Endereco endereco = new Endereco(entity.getCep(), entity.getRua(), entity.getNumEndereco(), entity.getBairro(), entity.getCidade(), entity.getComplemento());
        Cliente domain = new Cliente(entity.getNome(), telefone, endereco);
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
