package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(@NotBlank(message = "{obrigatorio}") String nome,
                                  @NotBlank(message = "{obrigatorio}") @Email(message = "{invalido}") String email,
                                  @NotBlank(message = "{obrigatorio}") String telefone,
                                  @Pattern(regexp = "^\\d{4,6}$", message = "{invalido}") String crm,
                                  @NotNull(message = "{obrigatorio}") Especialidade especialidade,
                                  @NotNull(message = "{obrigatorio}") @Valid DadosEndereco endereco) {
}
