package com.pedro.orso.softdesign.web.rest.dto;

import com.pedro.orso.softdesign.domain.AssociadoStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * A DTO for the {@link com.pedro.orso.softdesign.domain.Associado} entity.
 */
@Data
@NoArgsConstructor
public class AssociadoDto {

    private Long id;

    private String name;

    private String cpf;

    private AssociadoStatus status;

    private ZonedDateTime createDate;

    private ZonedDateTime modificationDate;
}
