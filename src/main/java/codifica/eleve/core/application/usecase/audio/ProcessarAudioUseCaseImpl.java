package codifica.eleve.core.application.usecase.audio;

import codifica.eleve.core.application.ports.in.ProcessarAudioUseCase;
import codifica.eleve.core.application.ports.out.ProcessarAudioIAPort;

public class ProcessarAudioUseCaseImpl implements ProcessarAudioUseCase {

    private final ProcessarAudioIAPort processarAudioIAPort;

    public ProcessarAudioUseCaseImpl(ProcessarAudioIAPort processarAudioIAPort) {
        this.processarAudioIAPort = processarAudioIAPort;
    }

    @Override
    public String execute(byte[] audioBytes, String mimeType, String prompt) {
        String instrucao = prompt != null && !prompt.isBlank()
                ? prompt
                : "Transcreva o áudio e responda de forma clara à solicitação feita nele.";

        return processarAudioIAPort.processarAudio(audioBytes, mimeType, instrucao);
    }
}
