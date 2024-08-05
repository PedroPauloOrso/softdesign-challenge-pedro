package com.pedro.orso.softdesign.web.rest.mapper;

import com.pedro.orso.softdesign.domain.Pauta;
import com.pedro.orso.softdesign.web.rest.dto.PautaDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Pauta} and its DTO {@link PautaDto}.
 */
@Mapper(componentModel = "spring")
public interface PautaMapper extends EntityMapper<PautaDto, Pauta> {

    PautaDto toDto(Pauta pauta);

    Pauta toEntity(PautaDto pautaDto);

}
