package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.agenda.*;
import codifica.eleve.core.application.usecase.agenda.calculadora.*;
import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.ServicoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgendaUseCaseConfig {

    @Bean
    public CreateAgendaUseCase createAgendaUseCase(AgendaRepository agendaRepository) {
        return new CreateAgendaUseCase(agendaRepository);
    }

    @Bean
    public FindAgendaByIdUseCase findAgendaByIdUseCase(AgendaRepository agendaRepository) {
        return new FindAgendaByIdUseCase(agendaRepository);
    }

    @Bean
    public ListAgendaUseCase listAgendaUseCase(AgendaRepository agendaRepository) {
        return new ListAgendaUseCase(agendaRepository);
    }

    @Bean
    public UpdateAgendaUseCase updateAgendaUseCase(AgendaRepository agendaRepository) {
        return new UpdateAgendaUseCase(agendaRepository);
    }

    @Bean
    public DeleteAgendaUseCase deleteAgendaUseCase(AgendaRepository agendaRepository) {
        return new DeleteAgendaUseCase(agendaRepository);
    }

    @Bean
    public CalcularServicoUseCase calcularServicoUseCase(PetRepository petRepository, ServicoRepository servicoRepository, ClienteRepository clienteRepository, CalcularDeslocamentoUseCase calcularDeslocamentoUseCase) {
        return new CalcularServicoUseCase(petRepository, servicoRepository, clienteRepository, calcularDeslocamentoUseCase);
    }

    @Bean
    public FilterAgendaUseCase filterAgendaUseCase(AgendaRepository agendaRepository) {
        return new FilterAgendaUseCase(agendaRepository);
    }

    @Bean
    public DisponibilidadeAgendaUseCase disponibilidadeAgendaUseCase(AgendaRepository agendaRepository) {
        return new DisponibilidadeAgendaUseCase(agendaRepository);
    }

    @Bean
    public CalcularLucroUseCase calcularLucroUseCase(AgendaRepository agendaRepository, DespesaRepository despesaRepository) {
        return new CalcularLucroUseCase(agendaRepository, despesaRepository);
    }

    @Bean
    public FindFutureAgendasByPetIdUseCase findFutureAgendasByPetIdUseCase(AgendaRepository agendaRepository) {
        return new FindFutureAgendasByPetIdUseCase(agendaRepository);
    }
}
