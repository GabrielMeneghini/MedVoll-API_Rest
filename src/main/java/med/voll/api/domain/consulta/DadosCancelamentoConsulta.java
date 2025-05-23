package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(@NotNull(message = "O id da consulta deve ser informado.") Long idConsulta,
                                        @NotNull(message = "O motivo da consulta deve ser informado.") MotivoCancelamento motivo) {
}
