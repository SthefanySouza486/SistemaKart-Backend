package br.com.kartclub.kartapi.repository;

import br.com.kartclub.kartapi.entity.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GestorRepository extends JpaRepository<Gestor,Long> {

    Optional<Gestor> findByEmail(String email);
}
