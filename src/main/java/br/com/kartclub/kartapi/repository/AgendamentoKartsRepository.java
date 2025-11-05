package br.com.kartclub.kartapi.repository;

import br.com.kartclub.kartapi.entity.AgendamentoKarts;
import br.com.kartclub.kartapi.entity.AgendamentoKartsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoKartsRepository extends JpaRepository<AgendamentoKarts, AgendamentoKartsId> {
}
