package br.com.kartclub.kartapi.dto.agendamento;

import br.com.kartclub.kartapi.dto.usuario.UsuarioResponseDTO;
import br.com.kartclub.kartapi.entity.enums.StatusAgendamento;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoResponseDTO {

    private Long idAgendamento;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private BigDecimal valorTotal;
    private Integer numeroParticipantes;
    private UsuarioResponseDTO usuario;

}
