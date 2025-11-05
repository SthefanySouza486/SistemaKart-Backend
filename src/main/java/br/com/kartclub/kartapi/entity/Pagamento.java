package br.com.kartclub.kartapi.entity;

import br.com.kartclub.kartapi.entity.enums.MetodoPagamento;
import br.com.kartclub.kartapi.entity.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @OneToOne
    @JoinColumn(name = "id_agendamento")
    private Agendamento agendamento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "metodo")
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private StatusPagamento statusPagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;
}
