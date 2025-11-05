package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.dto.usuario.UsuarioRequestDTO;
import br.com.kartclub.kartapi.entity.Usuario;
import br.com.kartclub.kartapi.entity.enums.TipoUsuario;
import br.com.kartclub.kartapi.exception.RegraNegocioException;
import br.com.kartclub.kartapi.exception.ResourceNotFoundException;
import br.com.kartclub.kartapi.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;


    @Transactional
    public Usuario cadastrarUsuario(UsuarioRequestDTO usuariodto) {
        if(repository.findByEmail(usuariodto.getEmail()).isPresent()){
            throw new RegraNegocioException("O email informado já está cadastrado!");
        }
        Usuario novousuario = new Usuario();
        novousuario.setNome(usuariodto.getNome());
        novousuario.setEmail(usuariodto.getEmail());
        novousuario.setTelefone(usuariodto.getTelefone());
        novousuario.setSenha(encoder.encode(usuariodto.getSenha()));
        novousuario.setTipoUsuario(TipoUsuario.CLIENTE);

        return repository.save(novousuario);
    }

    @Transactional
    public  Usuario atualizarUsuario(Long idUsuario, UsuarioRequestDTO usuariodto) {
        Usuario usuarioExistente = findById(idUsuario);

        usuarioExistente.setNome(usuariodto.getNome());
        usuarioExistente.setTelefone(usuariodto.getTelefone());

        return repository.save(usuarioExistente);
    }

    @Transactional
    public Usuario findById(Long idUsuario) {
        return repository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + idUsuario + " não encontrado!"));
    }
}
