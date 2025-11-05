package br.com.kartclub.kartapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class AgendamentoKartsId implements Serializable {

    @Column(name = "id_agendamento")
    private Long idAgendamento;

    @Column(name = "id_kart")
    private Long kartId;
}
