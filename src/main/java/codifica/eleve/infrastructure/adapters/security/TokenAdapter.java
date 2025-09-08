package codifica.eleve.infrastructure.adapters.security;

import codifica.eleve.core.application.ports.out.TokenPort;
import codifica.eleve.core.domain.shared.exceptions.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenAdapter implements TokenPort {

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${TOKEN_EXPIRATION_TIME}")
    private long expirationTime;

    private static final String ISSUER = "eleve";

    private final Set<String> tokenBlacklist = Collections.synchronizedSet(new HashSet<>());

    @Override
    public String generate(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secretKey));
    }

    @Override
    public String validate(String token) {
        if (tokenBlacklist.contains(token)) {
            throw new InvalidTokenException("Token de segurança inválido ou expirado. Faça o login novamente.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException("Token de segurança inválido ou expirado. Faça o login novamente.");
        }
    }

    @Override
    public void invalidate(String token) {
        tokenBlacklist.add(token);
    }
}
