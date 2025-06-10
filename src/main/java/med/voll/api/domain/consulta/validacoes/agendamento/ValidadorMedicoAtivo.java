package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if(!medicoRepository.existsByIdAndAtivoTrue(dados.idMedico())) {
            throw new ValidacaoException("O médico informado está inativo.");
        }
    }

}
