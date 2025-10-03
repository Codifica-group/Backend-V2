package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;
import codifica.eleve.infrastructure.persistence.solicitacao.SolicitacaoAgendaEntity;
import codifica.eleve.infrastructure.persistence.solicitacao.agenda_servico.SolicitacaoAgendaServicoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SolicitacaoAgendaMapper {

    private final ClienteRepository clienteRepository;
    private final RacaRepository racaRepository;

    public SolicitacaoAgendaMapper(ClienteRepository clienteRepository, RacaRepository racaRepository) {
        this.clienteRepository = clienteRepository;
        this.racaRepository = racaRepository;
    }

    public SolicitacaoAgendaEntity toEntity(SolicitacaoAgenda domain) {
        SolicitacaoAgendaEntity entity = new SolicitacaoAgendaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }
        entity.setChatId(domain.getChatId());

        PetEntity petEntity = new PetEntity();
        petEntity.setId(domain.getPet().getId().getValue());
        entity.setPet(petEntity);

        List<SolicitacaoAgendaServicoEntity> solicitacaoAgendaServicos = domain.getServicos().stream().map(servicoDomain -> {
            SolicitacaoAgendaServicoEntity solicitacaoAgendaServicoEntity = new SolicitacaoAgendaServicoEntity();
            solicitacaoAgendaServicoEntity.setSolicitacaoAgenda(entity);

            ServicoEntity servicoEntity = new ServicoEntity();
            servicoEntity.setId(servicoDomain.getId().getValue());
            solicitacaoAgendaServicoEntity.setServico(servicoEntity);

            solicitacaoAgendaServicoEntity.setValor(servicoDomain.getValor().getValor());

            return solicitacaoAgendaServicoEntity;
        }).collect(Collectors.toList());

        entity.setServicos(solicitacaoAgendaServicos);

        entity.setValorDeslocamento(domain.getValorDeslocamento().getValor());
        entity.setDataHoraInicio(domain.getDataHoraInicio());
        entity.setDataHoraFim(domain.getDataHoraFim());
        entity.setDataHoraSolicitacao(domain.getDataHoraSolicitacao());
        entity.setStatus(domain.getStatus());
        return entity;
    }

    public SolicitacaoAgenda toDomain(SolicitacaoAgendaEntity entity) {
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

        SolicitacaoAgenda domain = new SolicitacaoAgenda(entity.getChatId(), pet, servicos, valorDeslocamento, entity.getDataHoraInicio(), entity.getDataHoraFim(), entity.getDataHoraSolicitacao(), entity.getStatus());
        if (entity.getId() != null) {
            domain.setId(new Id(entity.getId()));
        }
        return domain;
    }
}
