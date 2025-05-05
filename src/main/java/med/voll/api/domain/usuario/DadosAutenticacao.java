package med.voll.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosAutenticacao(@Email(message = "Dados inválidos.")
                                @NotBlank(message = "{obrigatorio}")
                                String login,

                                @NotBlank(message = "{obrigatorio}")
                                @Size(min = 6, message = "{requimentosSenha}")
                                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$", message = "{requimentosSenha}")
                                String senha) {
}
