package codifica.eleve.config.security;

import codifica.eleve.core.application.ports.out.LoginAttemptPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class IpBlockFilter extends OncePerRequestFilter {

    private final LoginAttemptPort loginAttemptPort;

    @Value("${IP_BLOCK_ENABLED}")
    private boolean isIpBlockEnabled;

    public IpBlockFilter(LoginAttemptPort loginAttemptPort) {
        this.loginAttemptPort = loginAttemptPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isIpBlockEnabled) {
            String ip = getClientIP(request);
            if (loginAttemptPort.isBlocked(ip)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Bloqueio temporário por tentativas excessivas de login. Tente novamente mais tarde...");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        String proxyClientIP = request.getHeader("Proxy-Client-IP");
        if (proxyClientIP != null) {
            return proxyClientIP;
        }
        String wlProxyClientIP = request.getHeader("WL-Proxy-Client-IP");
        if (wlProxyClientIP != null) {
            return wlProxyClientIP;
        }
        return request.getRemoteAddr();
    }
}
