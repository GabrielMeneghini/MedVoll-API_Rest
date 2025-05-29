package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadoresAgendamentoConsultas;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadoresCancelamentoConsultas;

    // Methods
    public Consulta agendar(DadosAgendamentoConsulta dados) {

        // Tratando id do paciente
        var paciente = pacienteRepository.findById(dados.idPaciente());
        if(paciente.isEmpty()) {
          throw new ValidacaoException("Id do paciente informado não existe!");
        }

        // Tratando id do médico
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }
        Medico medico;
        if(dados.idMedico() == null) {
            medico = escolherMedicoDisponivelAleatoriamente(dados);
            if(medico == null) {
                throw new ValidacaoException("Não há nenhum médico disponível para essa data e hora.");
            }
        } else {
            medico = medicoRepository.getReferenceById(dados.idMedico());
        }

        System.out.println(medico);
        // Validações
        validadoresAgendamentoConsultas.forEach(v -> v.validar(dados));

        // Criando e salvando Consulta no DB
        var consulta = new Consulta(null, paciente.get(), medico, dados.dataHora(), null);
        consultaRepository.save(consulta);
        return consulta;
    }

    public void cancelar(DadosCancelamentoConsulta dados) {

        // Verificando se id da consulta existe
        if(!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informada não existe!");
        }
        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());

        // Validações
        validadoresCancelamentoConsultas.forEach(v -> v.validar(dados));

        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedicoDisponivelAleatoriamente(DadosAgendamentoConsulta dados) {
        if(dados.especialidade() == null) {
            throw new ValidacaoException("Se um médico não for escolhido o campo \"Especialidade\" deve ser preenchido.");
        }

        return medicoRepository.escolherMedicoAleatorioDisponivel(dados.especialidade(), dados.dataHora());
    }

}
