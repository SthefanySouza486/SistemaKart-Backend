package br.com.kartclub.kartapi.dto.kart;

import br.com.kartclub.kartapi.entity.enums.StatusKart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KartResponseDTO {

    private Long idKart;
    private Integer numero;
    private StatusKart statusKart;
}
