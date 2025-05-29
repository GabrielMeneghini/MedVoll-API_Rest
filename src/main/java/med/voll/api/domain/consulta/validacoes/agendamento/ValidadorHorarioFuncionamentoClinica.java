package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var diaDaSemana = dados.dataHora().getDayOfWeek();
        var hora = LocalTime.of(dados.dataHora().getHour(), dados.dataHora().getMinute());
        var horaAbre = LocalTime.of(7,0);
        var horaFecha = LocalTime.of(18,0);

        if(diaDaSemana.equals(DayOfWeek.SUNDAY) || (hora.isBefore(horaAbre) || hora.isAfter(horaFecha))) {
            throw new ValidacaoException("Você está tentando agendar uma consulta fora do horário de funcionamento da clínica. " +
                    "Estamos abertos de segunda a sábado, das 07:00 as 19:00.");
        }
    }

}
