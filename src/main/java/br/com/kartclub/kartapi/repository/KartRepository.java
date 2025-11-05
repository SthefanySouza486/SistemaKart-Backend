package br.com.kartclub.kartapi.repository;

import br.com.kartclub.kartapi.entity.Kart;
import br.com.kartclub.kartapi.entity.enums.StatusKart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {
    long countByStatus(StatusKart status);
    List<Kart> findAllByStatus(StatusKart status);
}
