package com.pedro.orso.softdesign.service;

import com.pedro.orso.softdesign.domain.Associado;
import com.pedro.orso.softdesign.domain.Pauta;
import com.pedro.orso.softdesign.exception.AssociadoException;
import com.pedro.orso.softdesign.repository.AssociadoRepository;
import com.pedro.orso.softdesign.web.rest.dto.AssociadoDto;
import com.pedro.orso.softdesign.web.rest.dto.PautaDto;
import com.pedro.orso.softdesign.web.rest.mapper.AssociadoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

    private static final long ID = 1L;
    private static final String CPF = "073.722.142-65";
    private static final String NAME = "Pedro";
    private static final ZonedDateTime CREATE_DATE = ZonedDateTime.now();
    private static final ZonedDateTime MODIFICATION_DATE = ZonedDateTime.now();

    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private AssociadoMapper associadoMapper;
    @InjectMocks
    private AssociadoService associadoService;

    @Test
    void shouldCreateAssociado() {
        // Given
        Associado associadoMock = createAssociadoEntity();
        given(associadoRepository.save(any(Associado.class))).willReturn(associadoMock);

        AssociadoDto associadoDtoMock = createAssociadoDtoEntity();

        given(associadoMapper.toDto(any(Associado.class))).willReturn(associadoDtoMock);
        given(associadoMapper.toEntity(any(AssociadoDto.class))).willReturn(associadoMock);

        // When
        var response = associadoService.createAssociado(associadoMapper.toDto(associadoMock));

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(ID);
    }

    @Test
    void shouldNotCreateDuplicateAssociado() {
        // Given
        Associado associadoMock = createAssociadoEntity();
        AssociadoDto associadoDtoMock = createAssociadoDtoEntity();

        given(associadoMapper.toDto(any(Associado.class))).willReturn(associadoDtoMock);

        // When
        when(associadoRepository.findByCpf(any())).thenReturn(Optional.of(associadoMock));

        AssociadoException.AssociadoAlreadyExistException exception =
                assertThrows(AssociadoException.AssociadoAlreadyExistException.class, () -> {
                    // Exception thrown
                    associadoService.createAssociado(associadoMapper.toDto(associadoMock));
                });

        // Then
        assertEquals("Associado com CPF já existe.", exception.getMessage());
    }

    @Test
    void shouldGetAssociadoByCpf() {
        // Given
        Associado associadoMock = createAssociadoEntity();
        given(associadoRepository.findByCpf(CPF)).willReturn(Optional.of(associadoMock));

        AssociadoDto associadoDtoMock = createAssociadoDtoEntity();

        given(associadoMapper.toDto(any(Associado.class))).willReturn(associadoDtoMock);

        // When
        var response = associadoService.getAssociadoByCpf(CPF);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(ID);
        assertThat(response.getCpf()).isEqualTo(CPF);
    }

    Associado createAssociadoEntity() {
        Associado entity = new Associado();
        entity.setId(ID);
        entity.setCpf(CPF);
        entity.setName(NAME);
        entity.setCreateDate(CREATE_DATE);
        entity.setModificationDate(MODIFICATION_DATE);
        return entity;
    }

    AssociadoDto createAssociadoDtoEntity() {
        AssociadoDto entity = new AssociadoDto();
        entity.setId(ID);
        entity.setCpf(CPF);
        entity.setName(NAME);
        entity.setCreateDate(CREATE_DATE);
        entity.setModificationDate(MODIFICATION_DATE);
        return entity;
    }

}