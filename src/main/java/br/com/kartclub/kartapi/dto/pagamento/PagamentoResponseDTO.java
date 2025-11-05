package br.com.kartclub.kartapi.dto.pagamento;

import br.com.kartclub.kartapi.entity.enums.MetodoPagamento;
import br.com.kartclub.kartapi.entity.enums.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoResponseDTO {

    private Long idPagamento;
    private Long idAgendamento;
    private BigDecimal valor;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;
    private LocalDateTime dataPagamento;
}
