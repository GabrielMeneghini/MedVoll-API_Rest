package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    boolean existsByIdAndAtivoTrue(Long idMedico);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.ativo = TRUE
            AND m.especialidade LIKE :especialidade
            AND m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE c.dataHora = :dataHora
                AND c.motivoCancelamento IS NULL
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Medico escolherMedicoAleatorioDisponivel(Especialidade especialidade, LocalDateTime dataHora);

}
