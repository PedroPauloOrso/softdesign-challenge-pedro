package com.pedro.orso.softdesign.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "pauta")
@NoArgsConstructor
public class Pauta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pauta_sequence")
    @SequenceGenerator(name = "pauta_sequence", sequenceName = "tb_pauta_sequence", allocationSize = 1)
    @Column(updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(max = 400)
    private String title;

    @Column(name = "total_run_time", nullable = false)
    private Duration totalRunTime;

    @Column(name = "is_started", nullable = false)
    private boolean isStarted = false;

    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "modification_date", nullable = false)
    private ZonedDateTime modificationDate;

    public boolean isClosed() {
        if (getStartDate() == null) {
            return false;
        }
        ZonedDateTime targetTime = getStartDate().plus(getTotalRunTime());
        return ZonedDateTime.now().isAfter(targetTime);
    }
}

