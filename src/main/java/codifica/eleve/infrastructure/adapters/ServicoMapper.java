package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper {

    public ServicoEntity toEntity(Servico domain) {
        ServicoEntity entity = new ServicoEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setNome(domain.getNome());
        if (domain.getValor() != null) {
            entity.setValorBase(domain.getValor().getValor());
        }
        return entity;
    }

    public Servico toDomain(ServicoEntity entity) {
        ValorMonetario valorMonetario = new ValorMonetario(entity.getValorBase());
        Servico domain = new Servico(entity.getNome(), valorMonetario);
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
