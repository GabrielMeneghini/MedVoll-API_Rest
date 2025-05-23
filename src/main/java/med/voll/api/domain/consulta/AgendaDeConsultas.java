package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

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
        } else {
            medico = medicoRepository.getReferenceById(dados.idMedico());
        }

        // Criando e salvando Consulta no DB
        var consulta = new Consulta(null, paciente.get(), medico, dados.dataHora());
        consultaRepository.save(consulta);
        return consulta;
    }

    private Medico escolherMedicoDisponivelAleatoriamente(DadosAgendamentoConsulta dados) {
        if(dados.especialidade() == null) {
            throw new ValidacaoException("Se um médico não for escolhido o campo \"Especialidade\" deve ser preenchido.");
        }

        return medicoRepository.escolherMedicoAleatorioDisponivel(dados.especialidade(), dados.dataHora());
    }


}
