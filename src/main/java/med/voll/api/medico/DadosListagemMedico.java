package med.voll.api.medico;

public record DadosListagemMedico(String nome,
                                  String email,
                                  String crm,
                                  Especialidade especialidade) {

    public DadosListagemMedico(Medico m) {
       this(m.getNome(), m.getEmail(), m.getCrm(), m.getEspecialidade());
    }
}
