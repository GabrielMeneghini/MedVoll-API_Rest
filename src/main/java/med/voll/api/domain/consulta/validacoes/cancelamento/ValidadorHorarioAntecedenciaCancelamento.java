package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedenciaCancelamento implements ValidadorCancelamentoConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        LocalDateTime dataHoraRequisicao = LocalDateTime.now();
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

        if(Duration.between(dataHoraRequisicao, consulta.getDataHora()).toHours() < 24) {
            throw new ValidacaoException("A consulta só pode ser cancelada com no mínimo 24 horas de antecedência.");
        }
    }

}
