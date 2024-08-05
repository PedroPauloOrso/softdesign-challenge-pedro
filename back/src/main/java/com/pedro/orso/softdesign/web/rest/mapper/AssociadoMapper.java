package com.pedro.orso.softdesign.web.rest.mapper;

import com.pedro.orso.softdesign.domain.Associado;
import com.pedro.orso.softdesign.web.rest.dto.AssociadoDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link com.pedro.orso.softdesign.domain.Associado} and its DTO {@link com.pedro.orso.softdesign.web.rest.dto.AssociadoDto}.
 */
@Mapper(componentModel = "spring")
public interface AssociadoMapper extends EntityMapper<AssociadoDto, Associado> {

    AssociadoDto toDto(Associado associado);

    Associado toEntity(AssociadoDto associadoDto);

}
