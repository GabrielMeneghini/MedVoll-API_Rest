package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNaMesmaDataHora implements ValidadorAgendamentoConsulta {

    @Autowired
    ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if(consultaRepository.existsBymotivoCancelamentoIsNullAndMedicoIdAndDataHora(dados.idMedico(), dados.dataHora())) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário.");
        }
    }

}
