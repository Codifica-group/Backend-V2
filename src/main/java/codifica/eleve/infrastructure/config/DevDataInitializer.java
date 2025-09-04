package codifica.eleve.infrastructure.config;

import codifica.eleve.infrastructure.persistence.agenda.AgendaJpaRepository;
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
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
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

        };
    }
}