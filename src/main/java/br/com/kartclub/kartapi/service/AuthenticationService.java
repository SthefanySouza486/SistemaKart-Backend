package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.dto.auth.LoginRequestDTO;
import br.com.kartclub.kartapi.dto.auth.LoginResponseDTO;
import br.com.kartclub.kartapi.entity.Gestor;
import br.com.kartclub.kartapi.entity.Usuario;
import br.com.kartclub.kartapi.repository.GestorRepository;
import br.com.kartclub.kartapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService  jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GestorRepository gestorRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getSenha()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String tipoUsuario = userDetails.getAuthorities().iterator().next().getAuthority();
        Long id;
        String nome;

        if ("GESTOR".equals(tipoUsuario)) {
            Gestor gestor = gestorRepository.findByEmail(request.getEmail()).orElseThrow();
            id = gestor.getIdGestor();
            nome = gestor.getNome();
        }else {
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
            id = usuario.getIdUsuario();
            nome = usuario.getNome();
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", id);
        extraClaims.put("nome", nome);
        extraClaims.put("role",  tipoUsuario);

        String jwtToken = jwtService.generateToken(userDetails, extraClaims);

        return new LoginResponseDTO(jwtToken, id, nome, tipoUsuario);
    }
}
