package br.com.kartclub.kartapi.repository;

import br.com.kartclub.kartapi.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.statusAgendamento <> 'CANCELADO' AND a.dataHora >= :inicio AND a.dataHora < :fim")
    boolean existsAgendamentoNoIntervalo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    List<Agendamento> findByUsuarioEmailOrderByDataHoraDesc(String email);
}
