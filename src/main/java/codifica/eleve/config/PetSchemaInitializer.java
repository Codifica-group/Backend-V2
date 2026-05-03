package codifica.eleve.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Component
public class PetSchemaInitializer implements ApplicationRunner {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public PetSchemaInitializer(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            if (!tabelaExiste(connection, "pet")) {
                return;
            }

            adicionarColunaSeNecessario(connection, "pet", "sexo", "ALTER TABLE pet ADD COLUMN sexo VARCHAR(50)");
            adicionarColunaSeNecessario(connection, "pet", "foto_url", "ALTER TABLE pet ADD COLUMN foto_url VARCHAR(2048)");
        }
    }

    private boolean tabelaExiste(Connection connection, String nomeTabela) throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet tabelas = metaData.getTables(connection.getCatalog(), null, nomeTabela, null)) {
            if (tabelas.next()) {
                return true;
            }
        }

        try (ResultSet tabelas = metaData.getTables(connection.getCatalog(), null, nomeTabela.toUpperCase(), null)) {
            return tabelas.next();
        }
    }

    private void adicionarColunaSeNecessario(
            Connection connection,
            String tabela,
            String coluna,
            String sqlAlteracao
    ) throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet colunas = metaData.getColumns(connection.getCatalog(), null, tabela, coluna)) {
            if (colunas.next()) {
                return;
            }
        }

        try (ResultSet colunas = metaData.getColumns(connection.getCatalog(), null, tabela.toUpperCase(), coluna.toUpperCase())) {
            if (colunas.next()) {
                return;
            }
        }

        jdbcTemplate.execute(sqlAlteracao);
    }
}
