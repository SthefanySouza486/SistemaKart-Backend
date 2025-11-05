package br.com.kartclub.kartapi.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private Long id;
    private String nome;
    private String tipoUsuario;
}
