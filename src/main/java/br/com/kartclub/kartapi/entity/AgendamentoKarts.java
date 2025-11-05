package br.com.kartclub.kartapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "agendamento_karts")
public class AgendamentoKarts {

    @EmbeddedId
    private AgendamentoKartsId id;

    @ManyToOne
    @MapsId("idAgendamento")
    @JoinColumn(name = "id_agendamento")
    private Agendamento agendamento;

    @ManyToOne
    @MapsId("kartId")
    @JoinColumn(name = "id_kart")
    private Kart kart;
}
