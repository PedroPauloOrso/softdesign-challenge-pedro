package com.pedro.orso.softdesign.service;

import com.pedro.orso.softdesign.domain.Pauta;
import com.pedro.orso.softdesign.repository.PautaRepository;
import com.pedro.orso.softdesign.web.rest.dto.PautaDto;
import com.pedro.orso.softdesign.web.rest.mapper.PautaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    private static final long ID = 1L;
    private static final String TITLE = "Title";
    private static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    private static final ZonedDateTime MODIFICATION_DATE = ZonedDateTime.now();

    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private PautaMapper pautaMapper;
    @InjectMocks
    private PautaService pautaService;

    @Test
    void shouldCreatePauta() {
        // Given
        Pauta pautaMock = createPautaEntity();
        given(pautaRepository.save(any(Pauta.class))).willReturn(pautaMock);

        PautaDto pautaDtoMock = new PautaDto();
        pautaDtoMock.setId(pautaMock.getId());
        pautaDtoMock.setTitle(pautaMock.getTitle());
        pautaDtoMock.setCreateDate(pautaMock.getCreateDate());
        pautaDtoMock.setModificationDate(pautaMock.getModificationDate());

        given(pautaMapper.toDto(any(Pauta.class))).willReturn(pautaDtoMock);
        given(pautaMapper.toEntity(any(PautaDto.class))).willReturn(pautaMock);

        // When
        var response = pautaService.createPauta(pautaMapper.toDto(pautaMock));

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(ID);
    }

    @Test
    void shouldGetPautaById() {
        // Given
        Pauta pautaMock = createPautaEntity();
        given(pautaRepository.findById(ID)).willReturn(Optional.of(pautaMock));

        PautaDto pautaDtoMock = new PautaDto();
        pautaDtoMock.setId(pautaMock.getId());
        pautaDtoMock.setTitle(pautaMock.getTitle());
        pautaDtoMock.setCreateDate(pautaMock.getCreateDate());
        pautaDtoMock.setModificationDate(pautaMock.getModificationDate());

        given(pautaMapper.toDto(any(Pauta.class))).willReturn(pautaDtoMock);

        // When
        var response = pautaService.getPautaById(ID);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(ID);
        assertThat(response.getTitle()).isEqualTo(TITLE);
    }

    @Test
    void shouldDeletePautaById() {
        // Given
        doNothing().when(pautaRepository).deleteById(ID);

        // When
        pautaService.deletePautaById(ID);

        // Then
        verify(pautaRepository, times(1)).deleteById(ID);
    }

    Pauta createPautaEntity() {
        Pauta entity = new Pauta();

        entity.setId(ID);
        entity.setTitle(TITLE);
        entity.setTotalRunTime(Duration.ofMinutes(1));
        entity.setCreateDate(CREATE_DATE);
        entity.setModificationDate(MODIFICATION_DATE);
        return entity;
    }

}