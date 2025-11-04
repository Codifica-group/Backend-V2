package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.application.ports.out.NotificationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketNotificationAdapter implements NotificationPort {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketNotificationAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notify(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }
}
