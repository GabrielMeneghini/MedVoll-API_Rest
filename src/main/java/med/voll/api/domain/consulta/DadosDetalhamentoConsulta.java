package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id,
                                        Long idPaciente,
                                        Long idMedico,
                                        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataHora) {

    public DadosDetalhamentoConsulta(Consulta c) {
        this(c.getId(), c.getPaciente().getId(), c.getMedico().getId(), c.getDataHora());
    }

}
