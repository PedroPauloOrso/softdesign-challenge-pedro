package com.pedro.orso.softdesign.web.rest.dto;

import com.pedro.orso.softdesign.domain.Associado;
import com.pedro.orso.softdesign.domain.Pauta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * A DTO for the {@link com.pedro.orso.softdesign.domain.Voto} entity.
 */
@Data
@NoArgsConstructor
public class VotoDto {

    private Long id;

    private Boolean voteOption;

    private Associado associado;

    private Pauta pauta;

    private ZonedDateTime createDate;

    private ZonedDateTime modificationDate;

}
