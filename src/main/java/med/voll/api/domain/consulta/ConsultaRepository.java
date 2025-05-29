package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsBymotivoCancelamentoIsNullAndMedicoIdAndDataHora(Long idMedico, LocalDateTime dataHora);

    boolean existsByPacienteIdAndDataHoraBetween(Long pacienteId, LocalDateTime primeiroHorarioDia, LocalDateTime ultimoHorarioDia);

}
