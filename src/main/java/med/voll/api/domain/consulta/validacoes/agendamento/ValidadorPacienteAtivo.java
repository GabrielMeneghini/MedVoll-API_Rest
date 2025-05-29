package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if(!pacienteRepository.pacienteAtivo(dados.idPaciente())) {
            throw new ValidacaoException("O paciente informado está inativo.");
        }
    }

}
