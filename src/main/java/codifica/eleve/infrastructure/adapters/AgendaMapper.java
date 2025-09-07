package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.infrastructure.persistence.agenda.AgendaEntity;
import codifica.eleve.infrastructure.persistence.agenda.agendaServico.AgendaServicoEntity;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AgendaMapper {

    public AgendaEntity toEntity(Agenda domain) {
        AgendaEntity entity = new AgendaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }

        PetEntity petEntity = new PetEntity();
        petEntity.setId(domain.getPet().getId().getValue());
        entity.setPet(petEntity);

        List<AgendaServicoEntity> agendaServicos = domain.getServicos().stream().map(servicoDomain -> {
            AgendaServicoEntity agendaServicoEntity = new AgendaServicoEntity();
            agendaServicoEntity.setAgenda(entity);

            ServicoEntity servicoEntity = new ServicoEntity();
            servicoEntity.setId(servicoDomain.getId().getValue());
            agendaServicoEntity.setServico(servicoEntity);

            agendaServicoEntity.setValor(servicoDomain.getValor().getValor());

            return agendaServicoEntity;
        }).collect(Collectors.toList());

        entity.setServicos(agendaServicos);

        entity.setValorDeslocamento(domain.getValorDeslocamento().getValor());
        entity.setDataHoraInicio(domain.getPeriodo().getInicio());
        entity.setDataHoraFim(domain.getPeriodo().getFim());
        return entity;
    }

    public Agenda toDomain(AgendaEntity entity) {
        Pet pet = new Pet();
        pet.setId(new Id(entity.getPet().getId()));
        pet.setNome(entity.getPet().getNome());

        List<Servico> servicos = entity.getServicos().stream().map(agendaServicoEntity -> {
            ServicoEntity servicoEntity = agendaServicoEntity.getServico();
            Servico servico = new Servico();
            servico.setId(new Id(servicoEntity.getId()));
            servico.setNome(servicoEntity.getNome());
            servico.setValor(new ValorMonetario(agendaServicoEntity.getValor()));
            return servico;
        }).collect(Collectors.toList());

        ValorMonetario valorDeslocamento = new ValorMonetario(entity.getValorDeslocamento());
        Periodo periodo = new Periodo(entity.getDataHoraInicio(), entity.getDataHoraFim());

        Agenda domain = new Agenda(pet, servicos, valorDeslocamento, periodo);
        domain.setId(new Id(entity.getId()));
        return domain;
    }
}
