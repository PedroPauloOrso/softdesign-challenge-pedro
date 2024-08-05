package com.pedro.orso.softdesign.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "associado")
@NoArgsConstructor
public class Associado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "associado_sequence")
    @SequenceGenerator(name = "associado_sequence", sequenceName = "tb_associado_sequence", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(max = 400)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssociadoStatus status;

    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "modification_date", nullable = false)
    private ZonedDateTime modificationDate;

}

