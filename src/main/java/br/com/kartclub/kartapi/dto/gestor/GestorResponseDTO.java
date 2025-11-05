package br.com.kartclub.kartapi.dto.gestor;

import br.com.kartclub.kartapi.entity.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GestorResponseDTO {

    private Long idGestor;
    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;
}
