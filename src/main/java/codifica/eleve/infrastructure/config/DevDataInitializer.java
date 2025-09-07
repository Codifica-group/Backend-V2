package codifica.eleve.infrastructure.config;

import codifica.eleve.infrastructure.persistence.agenda.AgendaEntity;
import codifica.eleve.infrastructure.persistence.agenda.AgendaJpaRepository;
import codifica.eleve.infrastructure.persistence.agenda.agendaServico.AgendaServicoEntity;
import codifica.eleve.infrastructure.persistence.cliente.ClienteEntity;
import codifica.eleve.infrastructure.persistence.cliente.ClienteJpaRepository;
import codifica.eleve.infrastructure.persistence.pet.PetEntity;
import codifica.eleve.infrastructure.persistence.pet.PetJpaRepository;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import codifica.eleve.infrastructure.persistence.raca.RacaJpaRepository;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteJpaRepository;
import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;
import codifica.eleve.infrastructure.persistence.servico.ServicoJpaRepository;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioEntity;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
public class DevDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DevDataInitializer.class);

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServicoJpaRepository servicoJpaRepository;

    @Autowired
    private PorteJpaRepository porteJpaRepository;

    @Autowired
    private RacaJpaRepository racaJpaRepository;

    @Autowired
    private PetJpaRepository petJpaRepository;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @Autowired
    private AgendaJpaRepository agendaJpaRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // POPULA BANCO DE DADOS
            // USUÁRIO ADMIN
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNome("Admin");
            admin.setEmail("admin@email.com");
            admin.setSenha(passwordEncoder.encode("Admin#2025"));
            usuarioJpaRepository.save(admin);

            // SERVIÇOS
            List<ServicoEntity> servicos = List.of(
                    new ServicoEntity("Banho", new BigDecimal(35.0)),
                    new ServicoEntity("Tosa", new BigDecimal(50.0)),
                    new ServicoEntity("Hidatação", new BigDecimal(15.0)));
            servicoJpaRepository.saveAll(servicos);

            // PORTES
            List<PorteEntity> portes = List.of(
                    new PorteEntity("Pequeno"),
                    new PorteEntity("Médio"),
                    new PorteEntity("Grande"));
            porteJpaRepository.saveAll(portes);

            // RAÇAS
            List<RacaEntity> racas = List.of(
                    new RacaEntity("Raça Test", portes.get(0)),
                    new RacaEntity( "Raça Test 2", portes.get(1)),
                    new RacaEntity( "Raça Test 3", portes.get(2)));
            racaJpaRepository.saveAll(racas);

            // CLIENTE
            List<ClienteEntity> clientes = (List.of(
                    new ClienteEntity("Cliente Test", "11900000000", "01001000", "Rua Test", "1", "Centro", "São Paulo", ""),
                    new ClienteEntity("Cliente Test 2", "11900000001", "06020010", "Rua Test 2", "140", "Vila Yara", "Osasco", "shopping"),
                    new ClienteEntity("Cliente Test 3", "11900000002", "06020194", "Rua Test 3", "2000", "Continental", "Osasco", "test")
            ));
            clienteJpaRepository.saveAll(clientes);

            // PET
            List<PetEntity> pets = List.of(
                    new PetEntity("Pet Test", racas.get(0), clientes.get(0)),
                    new PetEntity("Pet Test 2", racas.get(1), clientes.get(0)),
                    new PetEntity("Pet Test 3", racas.get(2), clientes.get(1)),
                    new PetEntity("Pet Test 4", racas.get(2), clientes.get(2))
            );
            petJpaRepository.saveAll(pets);

            // AGENDAS
            List<AgendaEntity> agendas = new ArrayList<>();
            LocalDateTime dataHoraInicio = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);

            // Agenda 1
            AgendaEntity agenda1 = new AgendaEntity();
            agenda1.setPet(pets.get(0));
            agenda1.setDataHoraInicio(dataHoraInicio);
            agenda1.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda1.setValorDeslocamento(new BigDecimal("0.0"));
            AgendaServicoEntity as1 = new AgendaServicoEntity();
            as1.setAgenda(agenda1);
            as1.setServico(servicos.get(0));
            as1.setValor(servicos.get(0).getValorBase());
            agenda1.getServicos().add(as1);
            agendas.add(agenda1);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 2
            AgendaEntity agenda2 = new AgendaEntity();
            agenda2.setPet(pets.get(0));
            agenda2.setDataHoraInicio(dataHoraInicio);
            agenda2.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda2.setValorDeslocamento(new BigDecimal("0.5"));
            AgendaServicoEntity as2 = new AgendaServicoEntity();
            as2.setAgenda(agenda2);
            as2.setServico(servicos.get(1));
            as2.setValor(servicos.get(1).getValorBase());
            agenda2.getServicos().add(as2);
            agendas.add(agenda2);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 3
            AgendaEntity agenda3 = new AgendaEntity();
            agenda3.setPet(pets.get(1));
            agenda3.setDataHoraInicio(dataHoraInicio);
            agenda3.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda3.setValorDeslocamento(new BigDecimal("1.0"));
            AgendaServicoEntity as3 = new AgendaServicoEntity();
            as3.setAgenda(agenda3);
            as3.setServico(servicos.get(2));
            as3.setValor(servicos.get(2).getValorBase());
            agenda3.getServicos().add(as3);
            agendas.add(agenda3);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 4
            AgendaEntity agenda4 = new AgendaEntity();
            agenda4.setPet(pets.get(1));
            agenda4.setDataHoraInicio(dataHoraInicio);
            agenda4.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda4.setValorDeslocamento(new BigDecimal("2.0"));
            AgendaServicoEntity as4a = new AgendaServicoEntity();
            as4a.setAgenda(agenda4);
            as4a.setServico(servicos.get(0));
            as4a.setValor(servicos.get(0).getValorBase());
            agenda4.getServicos().add(as4a);
            AgendaServicoEntity as4b = new AgendaServicoEntity();
            as4b.setAgenda(agenda4);
            as4b.setServico(servicos.get(1));
            as4b.setValor(servicos.get(1).getValorBase());
            agenda4.getServicos().add(as4b);
            agendas.add(agenda4);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 5
            AgendaEntity agenda5 = new AgendaEntity();
            agenda5.setPet(pets.get(2));
            agenda5.setDataHoraInicio(dataHoraInicio);
            agenda5.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda5.setValorDeslocamento(new BigDecimal("3.0"));
            AgendaServicoEntity as5a = new AgendaServicoEntity();
            as5a.setAgenda(agenda5);
            as5a.setServico(servicos.get(0));
            as5a.setValor(servicos.get(0).getValorBase());
            agenda5.getServicos().add(as5a);
            AgendaServicoEntity as5b = new AgendaServicoEntity();
            as5b.setAgenda(agenda5);
            as5b.setServico(servicos.get(2));
            as5b.setValor(servicos.get(2).getValorBase());
            agenda5.getServicos().add(as5b);
            agendas.add(agenda5);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 6
            AgendaEntity agenda6 = new AgendaEntity();
            agenda6.setPet(pets.get(2));
            agenda6.setDataHoraInicio(dataHoraInicio);
            agenda6.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda6.setValorDeslocamento(new BigDecimal("4.0"));
            AgendaServicoEntity as6a = new AgendaServicoEntity();
            as6a.setAgenda(agenda6);
            as6a.setServico(servicos.get(1));
            as6a.setValor(servicos.get(1).getValorBase());
            agenda6.getServicos().add(as6a);
            AgendaServicoEntity as6b = new AgendaServicoEntity();
            as6b.setAgenda(agenda6);
            as6b.setServico(servicos.get(2));
            as6b.setValor(servicos.get(2).getValorBase());
            agenda6.getServicos().add(as6b);
            agendas.add(agenda6);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 7
            AgendaEntity agenda7 = new AgendaEntity();
            agenda7.setPet(pets.get(3));
            agenda7.setDataHoraInicio(dataHoraInicio);
            agenda7.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda7.setValorDeslocamento(new BigDecimal("5.0"));
            for (ServicoEntity servico : servicos) {
                AgendaServicoEntity as7 = new AgendaServicoEntity();
                as7.setAgenda(agenda7);
                as7.setServico(servico);
                as7.setValor(servico.getValorBase());
                agenda7.getServicos().add(as7);
            }
            agendas.add(agenda7);

            dataHoraInicio = dataHoraInicio.plusHours(1);

            // Agenda 8
            AgendaEntity agenda8 = new AgendaEntity();
            agenda8.setPet(pets.get(3));
            agenda8.setDataHoraInicio(dataHoraInicio);
            agenda8.setDataHoraFim(dataHoraInicio.plusHours(1));
            agenda8.setValorDeslocamento(new BigDecimal("6.0"));
            AgendaServicoEntity as8 = new AgendaServicoEntity();
            as8.setAgenda(agenda8);
            as8.setServico(servicos.get(0));
            as8.setValor(servicos.get(0).getValorBase());
            agenda8.getServicos().add(as8);
            agendas.add(agenda8);

            agendaJpaRepository.saveAll(agendas);
        };
    }
}
