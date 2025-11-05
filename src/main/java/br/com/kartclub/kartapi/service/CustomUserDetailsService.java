package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.entity.Gestor;
import br.com.kartclub.kartapi.entity.Usuario;
import br.com.kartclub.kartapi.repository.GestorRepository;
import br.com.kartclub.kartapi.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GestorRepository gestorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("Tentando carregar usuário pelo email: {}", email);

        Optional<Gestor> gestorOpt = gestorRepository.findByEmail(email);
        if (gestorOpt.isPresent()) {
            Gestor gestor = gestorOpt.get();
            Collection<? extends GrantedAuthority> authorities = getAuthorities(gestor.getTipoUsuario().name());
            log.info("Gestor encontrado. Email: {}, Autoridades: {}", gestor.getEmail(), authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
            return new User(gestor.getEmail(), gestor.getSenha(), authorities);
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Collection<? extends GrantedAuthority> authorities = getAuthorities(usuario.getTipoUsuario().name());
            log.info("Cliente encontrado. Email: {}, Autoridades: {}", usuario.getEmail(), authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
            return new User(usuario.getEmail(), usuario.getSenha(), authorities);
        }

        log.warn("Usuário não encontrado com o email: {}", email);
        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role){
        return List.of(new SimpleGrantedAuthority(role));
    }
}
