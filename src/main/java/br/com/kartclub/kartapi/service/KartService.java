package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.entity.Kart;
import br.com.kartclub.kartapi.entity.enums.StatusKart;
import br.com.kartclub.kartapi.exception.ResourceNotFoundException;
import br.com.kartclub.kartapi.repository.KartRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KartService {

    @Autowired
    private KartRepository repository;

    @Transactional
    public List<Kart> buscarTodos() {
        return repository.findAll();
    }

    @Transactional
    public Kart buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kart com ID " + id + " não encontrado!"));
    }

    @Transactional
    public Kart atualizarStatus(Long id, StatusKart novoStatus) {
        Kart kart = buscarPorId(id);
        kart.setStatus(novoStatus);
        return repository.save(kart);
    }

    @Transactional
    public Kart cadastrarKart(Integer numero){
        Kart novoKart = new Kart();
        novoKart.setNumero(numero);
        novoKart.setStatus(StatusKart.DISPONIVEL);

        return repository.save(novoKart);
    }
}
