package br.com.kartclub.kartapi.repository;

import br.com.kartclub.kartapi.entity.Preco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrecoRepository extends JpaRepository<Preco, Long> {

    Optional<Preco> findFirstByAtivoTrue ();
}
