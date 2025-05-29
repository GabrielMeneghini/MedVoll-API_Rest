package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedenciaAgendamento implements ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var horaAtual = LocalDateTime.now();
        var horaConsulta = dados.dataHora();
        var antecedenciaEmMinutos = Duration.between(horaAtual, horaConsulta).toMinutes();

        if(antecedenciaEmMinutos < 30) {
            throw new ValidacaoException("As consultas devem ser agendadas com no mínimo 30 minutos de antecedência.");
        }
    }
}
