package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(@NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataHora,
                                       @NotNull Long idPaciente,
                                       Long idMedico,
                                       Especialidade especialidade) {
}
