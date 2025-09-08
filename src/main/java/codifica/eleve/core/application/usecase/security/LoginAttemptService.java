package codifica.eleve.core.application.usecase.security;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    @Value("${MAX_LOGIN_ATTEMPTS}")
    private int maxLoginAttempts;

    @Value("${BLOCK_DURATION_MINUTES}")
    private int blockDurationMinutes;

    private LoadingCache<String, Integer> attemptsCache;

    @PostConstruct
    public void init() {
        attemptsCache = Caffeine.newBuilder()
                .expireAfterWrite(blockDurationMinutes, TimeUnit.MINUTES)
                .build(key -> 0);
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = attemptsCache.get(key);
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        return attemptsCache.get(key) >= maxLoginAttempts;
    }

    public int getAttempts(String key) {
        return attemptsCache.get(key);
    }

    public int getBlockDurationMinutes() {
        return blockDurationMinutes;
    }
}
