package com.pedro.orso.softdesign.service;

import com.pedro.orso.softdesign.domain.Associado;
import com.pedro.orso.softdesign.domain.AssociadoStatus;
import com.pedro.orso.softdesign.domain.Pauta;
import com.pedro.orso.softdesign.domain.Voto;
import com.pedro.orso.softdesign.exception.AssociadoException;
import com.pedro.orso.softdesign.exception.PautaException;
import com.pedro.orso.softdesign.exception.VotoException;
import com.pedro.orso.softdesign.repository.AssociadoRepository;
import com.pedro.orso.softdesign.repository.PautaRepository;
import com.pedro.orso.softdesign.repository.VotoRepository;
import com.pedro.orso.softdesign.web.rest.dto.VotoDto;
import com.pedro.orso.softdesign.web.rest.mapper.VotoMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

@SpringBootTest
public class VotoServiceTest {

    private static final Long PAUTA_ID = 1L;
    private static final Long ASSOCIADO_ID = 2L;
    private static final Long VOTO_ID = 3L;
    private static final ZonedDateTime NOW = ZonedDateTime.now();

    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private VotoRepository votoRepository;
    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private VotoMapper votoMapper;

    @InjectMocks
    private VotoService votoService;

    @Test
    void shouldCreateVoto() {
        // Given
        VotoDto votoDto = createVotoDto();
        Voto votoMock = createVotoEntity();

        given(votoMapper.toEntity(any(VotoDto.class))).willReturn(votoMock);
        given(votoMapper.toDto(any(Voto.class))).willReturn(votoDto);
        given(pautaRepository.findById(PAUTA_ID)).willReturn(Optional.of(createPautaEntity()));
        given(associadoRepository.findById(ASSOCIADO_ID)).willReturn(Optional.of(createAssociadoEntity()));
        given(votoRepository.save(votoMock)).willReturn(votoMock);

        // When
        VotoDto response = votoService.createVoto(votoDto);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(VOTO_ID);
    }

    @Test
    void shouldNotCreateVotoIfPautaDoesNotExist() {
        // Given
        VotoDto votoDto = createVotoDto();
        Voto votoMock = createVotoEntity();

        given(votoMapper.toEntity(any(VotoDto.class))).willReturn(votoMock);
        given(pautaRepository.findById(PAUTA_ID)).willReturn(Optional.empty());

        // When
        PautaException.PautaDoesNotExistException exception =
                assertThrows(PautaException.PautaDoesNotExistException.class, () -> {
                    votoService.createVoto(votoDto);
                });

        // Then
        assertThat(exception.getMessage()).isEqualTo("A pauta não existe.");
    }

    @Test
    void shouldNotCreateVotoIfAssociadoDoesNotExist() {
        // Given
        VotoDto votoDto = createVotoDto();
        Voto votoMock = createVotoEntity();

        given(votoMapper.toEntity(any(VotoDto.class))).willReturn(votoMock);
        given(pautaRepository.findById(PAUTA_ID)).willReturn(Optional.of(createPautaEntity()));
        given(associadoRepository.findById(ASSOCIADO_ID)).willReturn(Optional.empty());

        // When
        AssociadoException.AssociadoDoesNotExistException exception =
                assertThrows(AssociadoException.AssociadoDoesNotExistException.class, () -> {
                    votoService.createVoto(votoDto);
                });

        // Then
        assertThat(exception.getMessage()).isEqualTo("Associado não existe.");
    }

    @Test
    void shouldNotCreateDuplicateVoto() {
        // Given
        VotoDto votoDto = createVotoDto();
        Voto votoMock = createVotoEntity();

        given(votoMapper.toEntity(any(VotoDto.class))).willReturn(votoMock);
        given(pautaRepository.findById(PAUTA_ID)).willReturn(Optional.of(createPautaEntity()));
        given(associadoRepository.findById(ASSOCIADO_ID)).willReturn(Optional.of(createAssociadoEntity()));
        given(votoRepository.findByAssociadoIdAndPautaId(ASSOCIADO_ID, PAUTA_ID)).willReturn(Optional.of(votoMock));

        // When
        VotoException.VotoAlreadyExistsException exception =
                assertThrows(VotoException.VotoAlreadyExistsException.class, () -> {
                    votoService.createVoto(votoDto);
                });

        // Then
        assertThat(exception.getMessage()).isEqualTo("O voto já existe.");
    }

    private Voto createVotoEntity() {
        Voto voto = new Voto();
        voto.setId(VOTO_ID);
        voto.setVoteOption(true);
        voto.setPauta(createPautaEntity());
        voto.setAssociado(createAssociadoEntity());
        voto.setCreateDate(NOW);
        voto.setModificationDate(NOW);
        return voto;
    }

    private VotoDto createVotoDto() {
        VotoDto votoDto = new VotoDto();
        votoDto.setId(VOTO_ID);
        votoDto.setVoteOption(true);
        votoDto.setPauta(createPautaEntity());
        votoDto.setAssociado(createAssociadoEntity());
        return votoDto;
    }

    private Pauta createPautaEntity() {
        Pauta pauta = new Pauta();
        pauta.setId(PAUTA_ID);
        pauta.setStarted(true);
        return pauta;
    }

    private Associado createAssociadoEntity() {
        Associado associado = new Associado();
        associado.setId(ASSOCIADO_ID);
        associado.setStatus(AssociadoStatus.ABLE_TO_VOTE);
        return associado;
    }

}