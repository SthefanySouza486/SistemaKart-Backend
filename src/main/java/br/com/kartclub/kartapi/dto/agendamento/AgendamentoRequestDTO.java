package br.com.kartclub.kartapi.dto.agendamento;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoRequestDTO {

    @NotNull(message = "A data e hora são obrigatórias!")
    @FutureOrPresent(message = "A data do agendamento não pode ser no passado!")
    private LocalDateTime dataHora;

    @NotNull(message = "O número de participantes é obrigatório!")
    @Min(value = 5, message = "Deve ter pelo menos 5 participantes.")
    private Integer numeroParticipantes;
}
