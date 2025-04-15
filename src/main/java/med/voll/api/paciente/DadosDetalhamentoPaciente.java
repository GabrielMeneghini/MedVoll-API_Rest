package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoPaciente(Long id,
                                        String nome,
                                        String email,
                                        String telefone,
                                        String cpf,
                                        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente p) {
        this(p.getId(), p.getNome(), p.getEmail(), p.getTelefone(), p.getCpf(), p.getEndereco());
    }
}
