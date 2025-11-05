package br.com.kartclub.kartapi.entity;

import br.com.kartclub.kartapi.entity.enums.StatusKart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "kart")
@Getter
@Setter
public class Kart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kart")
    private Long idKart;

    @Column(name = "numero")
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private StatusKart status;
}
