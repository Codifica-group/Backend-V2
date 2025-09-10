package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.infrastructure.persistence.agenda.AgendaEntity;
import codifica.eleve.infrastructure.persistence.agenda.agendaServico.AgendaServicoEntity;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AgendaMapper {

    private final ClienteRepository clienteRepository;
    private final RacaRepository racaRepository;

    public AgendaMapper(ClienteRepository clienteRepository, RacaRepository racaRepository) {
        this.clienteRepository = clienteRepository;
        this.racaRepository = racaRepository;
    }

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

        Cliente cliente = clienteRepository.findById(entity.getPet().getCliente().getId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        pet.setCliente(cliente);

        Raca raca = racaRepository.findById(entity.getPet().getRaca().getId())
                .orElseThrow(() -> new NotFoundException("Raça não encontrada"));
        pet.setRaca(raca);

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
