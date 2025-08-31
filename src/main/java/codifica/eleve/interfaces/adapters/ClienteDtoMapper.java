package codifica.eleve.interfaces.adapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Telefone;
import codifica.eleve.interfaces.dto.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteDtoMapper {
    public Cliente toDomain(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Telefone telefone = new Telefone(dto.getTelefone());
        Endereco endereco = new Endereco(dto.getCep(), dto.getRua(), dto.getNumEndereco(), dto.getBairro(), dto.getCidade(), dto.getComplemento());
        Cliente domain = new Cliente(dto.getNome(), telefone, endereco);
        if (dto.getId() != null) {
            domain.setId(new Id(dto.getId()));
        }
        return domain;
    }

    public ClienteDTO toDto(Cliente domain) {
        if (domain == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        if (domain.getId() != null) {
            dto.setId(domain.getId().getValue());
        }
        dto.setNome(domain.getNome());
        if (domain.getTelefone() != null) {
            dto.setTelefone(domain.getTelefone().getNumero());
        }
        if (domain.getEndereco() != null) {
            dto.setCep(domain.getEndereco().getCep());
            dto.setRua(domain.getEndereco().getRua());
            dto.setNumEndereco(domain.getEndereco().getNumero());
            dto.setBairro(domain.getEndereco().getBairro());
            dto.setCidade(domain.getEndereco().getCidade());
            dto.setComplemento(domain.getEndereco().getComplemento());
        }
        return dto;
    }
}
