package br.com.kartclub.kartapi.dto.usuario;

import br.com.kartclub.kartapi.entity.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private TipoUsuario tipoUsuario;
}
