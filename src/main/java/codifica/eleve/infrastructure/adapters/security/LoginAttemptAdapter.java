package codifica.eleve.infrastructure.adapters.security;

import codifica.eleve.core.application.ports.out.LoginAttemptPort;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LoginAttemptAdapter implements LoginAttemptPort {

    @Value("${MAX_LOGIN_ATTEMPTS:5}")
    private int maxLoginAttempts;

    @Value("${BLOCK_DURATION_MINUTES:15}")
    private int blockDurationMinutes;

    private LoadingCache<String, Integer> attemptsCache;

    @PostConstruct
    public void init() {
        attemptsCache = Caffeine.newBuilder()
                .expireAfterWrite(blockDurationMinutes, TimeUnit.MINUTES)
                .build(key -> 0);
    }

    @Override
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    @Override
    public void loginFailed(String key) {
        int attempts = attemptsCache.get(key);
        attempts++;
        attemptsCache.put(key, attempts);
    }

    @Override
    public boolean isBlocked(String key) {
        return attemptsCache.get(key) >= maxLoginAttempts;
    }

    @Override
    public int getAttempts(String key) {
        return attemptsCache.get(key);
    }

    @Override
    public int getBlockDurationMinutes() {
        return blockDurationMinutes;
    }
}
