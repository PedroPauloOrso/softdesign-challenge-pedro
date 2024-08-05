package com.pedro.orso.softdesign.web.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * A DTO for the {@link com.pedro.orso.softdesign.domain.Pauta} entity.
 */
@Data
@NoArgsConstructor
public class PautaDto {

    private Long id;

    private String title;

    private Duration totalRunTime;

    private boolean isStarted;

    private boolean isClosed;

    private ZonedDateTime startDate;

    private ZonedDateTime createDate;

    private ZonedDateTime modificationDate;

}
