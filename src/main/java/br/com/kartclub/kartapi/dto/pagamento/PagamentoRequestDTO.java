package br.com.kartclub.kartapi.dto.pagamento;

import br.com.kartclub.kartapi.entity.enums.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequestDTO {

    @NotNull(message = "O método de pagamento é obrigatório!")
    private MetodoPagamento metodoPagamento;
}
