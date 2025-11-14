package codifica.eleve.infrastructure.cache;

import codifica.eleve.core.application.usecase.cliente.*;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.shared.Pagina;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Esta classe contém os Decorators de Caching para os UseCases de Cliente.
 * As anotações do Spring Cache são aplicadas aqui, fora do 'core'.
 */
public class ClienteCachingDecorators {

    @Component
    public static class CachingListClienteUseCase extends ListClienteUseCase {
        public CachingListClienteUseCase(ClienteRepository clienteRepository) {
            super(clienteRepository);
        }

        @Override
        @Cacheable(cacheNames = "clientes", key = "#offset + '-' + #size")
        public Pagina<Cliente> execute(int offset, int size) {
            return super.execute(offset, size);
        }
    }

    @Component
    public static class CachingCreateClienteUseCase extends CreateClienteUseCase {
        public CachingCreateClienteUseCase(ClienteRepository clienteRepository) {
            super(clienteRepository);
        }

        @Override
        @CacheEvict(cacheNames = "clientes", allEntries = true)
        public Map<String, Object> execute(Cliente cliente) {
            return super.execute(cliente);
        }
    }

    @Component
    public static class CachingUpdateClienteUseCase extends UpdateClienteUseCase {
        public CachingUpdateClienteUseCase(ClienteRepository clienteRepository) {
            super(clienteRepository);
        }

        @Override
        @CacheEvict(cacheNames = "clientes", allEntries = true)
        public String execute(Integer id, Cliente cliente) {
            return super.execute(id, cliente);
        }
    }

    @Component
    public static class CachingDeleteClienteUseCase extends DeleteClienteUseCase {
        public CachingDeleteClienteUseCase(ClienteRepository clienteRepository, PetRepository petRepository) {
            super(clienteRepository, petRepository);
        }

        @Override
        @CacheEvict(cacheNames = "clientes", allEntries = true)
        public void execute(Integer id) {
            super.execute(id);
        }
    }
}
