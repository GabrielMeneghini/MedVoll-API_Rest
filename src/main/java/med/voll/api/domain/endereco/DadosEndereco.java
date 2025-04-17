package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(@NotBlank(message = "{obrigatorio}")
                            String logradouro,

                            @NotBlank(message = "{obrigatorio}")
                            String bairro,

                            @NotBlank(message = "{obrigatorio}")
                            @Pattern(regexp = "^\\d{8}$", message = "{invalido}")
                            String cep,

                            @NotBlank(message = "{obrigatorio}")
                            String cidade,

                            @NotBlank(message = "{obrigatorio}")
                            String uf,

                            String numero,

                            String complemento) {
}
