package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.ports.in.ProcessarAudioUseCase;
import codifica.eleve.core.application.ports.out.ProcessarAudioIAPort;
import codifica.eleve.core.application.usecase.audio.ProcessarAudioUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AudioUseCaseConfig {

    @Bean
    public ProcessarAudioUseCase processarAudioUseCase(ProcessarAudioIAPort processarAudioIAPort) {
        return new ProcessarAudioUseCaseImpl(processarAudioIAPort);
    }
}
