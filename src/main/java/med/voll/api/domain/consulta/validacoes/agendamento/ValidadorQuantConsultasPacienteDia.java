package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorQuantConsultasPacienteDia implements ValidadorAgendamentoConsulta {

    @Autowired
    ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorarioDia = dados.dataHora().withHour(7);
        var ultimoHorarioDia = dados.dataHora().withHour(18);


        if(consultaRepository.existsByPacienteIdAndDataHoraBetween(dados.idPaciente(), primeiroHorarioDia, ultimoHorarioDia)) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse mesmo dia");
        }
    }
}
