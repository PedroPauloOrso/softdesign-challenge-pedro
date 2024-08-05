package com.pedro.orso.softdesign.service;

import com.pedro.orso.softdesign.domain.AssociadoStatus;
import com.pedro.orso.softdesign.domain.Voto;
import com.pedro.orso.softdesign.exception.AssociadoException;
import com.pedro.orso.softdesign.exception.PautaException;
import com.pedro.orso.softdesign.exception.VotoException;
import com.pedro.orso.softdesign.repository.AssociadoRepository;
import com.pedro.orso.softdesign.repository.PautaRepository;
import com.pedro.orso.softdesign.repository.VotoRepository;
import com.pedro.orso.softdesign.web.rest.dto.PautaVoteCountDto;
import com.pedro.orso.softdesign.web.rest.dto.VotoDto;
import com.pedro.orso.softdesign.web.rest.mapper.VotoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotoService {

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;

    private final VotoMapper votoMapper;

    /**
     * Creates a Voto.
     *
     * @param votoDto the entity to create.
     * @return the persisted entity.
     */
    @Transactional
    public VotoDto createVoto(VotoDto votoDto) {

        if (votoDto == null) {
            throw new VotoException.VotoIsInvalid();
        }

        log.info("Creating new voto with from cpf: {} to pauta ID: {}", votoDto.getAssociado().getCpf(), votoDto.getPauta().getId());

        Voto voto = votoMapper.toEntity(votoDto);

        // Validate and Fetch data
        LoadPautaAndAssociado(voto);

        // Pauta Validation
        ValidatePauta(voto);

        // Associado Validation
        ValidateAssociado(voto);

        // Voto Validation
        ValidateVoto(voto);

        voto.setId(null); // Sequence
        voto.setCreateDate(ZonedDateTime.now());
        voto.setModificationDate(ZonedDateTime.now());

        Voto savedVoto = votoRepository.save(voto);
        log.info("Voto created successfully with id: {}", savedVoto.getId());

        return votoMapper.toDto(savedVoto);
    }

    private void LoadPautaAndAssociado(Voto voto) {

        // Invalid pauta
        if (voto.getPauta() == null || voto.getPauta().getId() == null) {
            throw new PautaException.PautaIsInvalid();
        }

        // Invalid Associado
        if (voto.getAssociado() == null || voto.getAssociado().getId() == null) {
            throw new AssociadoException.AssociadoIsInvalid();
        }

        voto.setPauta(pautaRepository.findById(voto.getPauta().getId()).orElseThrow(PautaException.PautaDoesNotExistException::new));
        voto.setAssociado(associadoRepository.findById(voto.getAssociado().getId()).orElseThrow(AssociadoException.AssociadoDoesNotExistException::new));
    }

    private void ValidatePauta(Voto voto) {
        // Not open
        if (!voto.getPauta().isStarted()) {
            throw new PautaException.PautaIsNotOpen();
        }

        // Closed pauta
        if (voto.getPauta().isClosed()) {
            throw new PautaException.PautaIsClosed();
        }
    }

    private void ValidateAssociado(Voto voto) {
        // Not open
        if (voto.getAssociado().getStatus() == AssociadoStatus.UNABLE_TO_VOTE) {
            throw new AssociadoException.AssociadoCantVoteException();
        }
    }

    private void ValidateVoto(Voto voto) {

        // If already exists
        var checkVoto = votoRepository.findByAssociadoIdAndPautaId(voto.getAssociado().getId(), voto.getPauta().getId());

        if (checkVoto.isPresent()) {
            throw new VotoException.VotoAlreadyExistsException();
        }
    }

    public PautaVoteCountDto getPautaVoteCount(Long id) {
        pautaRepository.findById(id).orElseThrow(PautaException.PautaDoesNotExistException::new);
        return votoRepository.getVotoCountByPautaId(id);
    }
}
