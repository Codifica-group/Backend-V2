package codifica.eleve.infrastructure.adapters.security;

import codifica.eleve.core.application.ports.out.RequestContextPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RequestContextAdapter implements RequestContextPort {
    @Override
    public String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
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

    @Override
    public String getUserAgent() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("User-Agent");
    }
}
