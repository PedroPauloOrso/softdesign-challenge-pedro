package com.pedro.orso.softdesign.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "voto")
@NoArgsConstructor
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voto_sequence")
    @SequenceGenerator(name = "voto_sequence", sequenceName = "tb_voto_sequence", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    @Column(name = "vote_option", nullable = false)
    private Boolean voteOption;

    @ManyToOne
    @JoinColumn(name = "associado_id", nullable = false)
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "modification_date", nullable = false)
    private ZonedDateTime modificationDate;

}

