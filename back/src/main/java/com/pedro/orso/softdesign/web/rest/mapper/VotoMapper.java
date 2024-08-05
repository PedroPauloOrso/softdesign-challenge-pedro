package com.pedro.orso.softdesign.web.rest.mapper;

import com.pedro.orso.softdesign.domain.Pauta;
import com.pedro.orso.softdesign.domain.Voto;
import com.pedro.orso.softdesign.web.rest.dto.VotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Voto} and its DTO {@link com.pedro.orso.softdesign.web.rest.dto.VotoDto}.
 */
@Mapper(componentModel = "spring", uses = {PautaMapper.class})
public interface VotoMapper extends EntityMapper<VotoDto, Voto> {

    @Mapping(target = "pauta.id", source = "pauta.id")
    VotoDto toDto(Voto voto);

    @Mapping(target = "pauta.id", source = "pauta.id")
    Voto toEntity(VotoDto votoDto);
}
