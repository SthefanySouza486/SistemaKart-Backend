package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.dto.gestor.GestorRequestDTO;
import br.com.kartclub.kartapi.entity.Gestor;
import br.com.kartclub.kartapi.entity.enums.TipoUsuario;
import br.com.kartclub.kartapi.exception.RegraNegocioException;
import br.com.kartclub.kartapi.exception.ResourceNotFoundException;
import br.com.kartclub.kartapi.repository.GestorRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GestorService {

    @Autowired
    private GestorRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Gestor cadastrarGestor(GestorRequestDTO gestordto) {
        if(repository.findByEmail(gestordto.getEmail()).isPresent()){
            throw new RegraNegocioException("O email informado já está cadastrado!");
        }

        Gestor novogestor = new Gestor();
        novogestor.setEmail(gestordto.getEmail());
        novogestor.setNome(gestordto.getNome());
        novogestor.setSenha(encoder.encode(gestordto.getSenha()));
        novogestor.setTipoUsuario(TipoUsuario.GESTOR);

        return repository.save(novogestor);
    }

    @Transactional
    public Gestor atualizarGestor(Long idGestor, GestorRequestDTO gestordto) {

        Gestor gestorExistente = buscarPorId(idGestor);

        String novoEmail = gestordto.getEmail();
        if(!novoEmail.equals(gestorExistente.getEmail())){
            if(repository.findByEmail(novoEmail).isPresent()){
                throw new RegraNegocioException("O novo email informado já está cadastrado!");
            }
            gestorExistente.setEmail(novoEmail);
        }
        gestorExistente.setNome(gestordto.getNome());
        if (gestordto.getSenha() != null && !gestordto.getSenha().isEmpty()) {
            gestorExistente.setSenha(encoder.encode(gestordto.getSenha()));
        }
        return repository.save(gestorExistente);
    }

    @Transactional
    public Gestor buscarPorId(Long idGestor) {
        return repository.findById(idGestor)
                .orElseThrow(() -> new ResourceNotFoundException("Gestor com ID " + idGestor + " não encontrado!"));
    }
}
