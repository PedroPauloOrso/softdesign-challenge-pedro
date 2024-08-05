package com.pedro.orso.softdesign.service;

import com.pedro.orso.softdesign.domain.Pauta;
import com.pedro.orso.softdesign.exception.PautaException;
import com.pedro.orso.softdesign.repository.PautaRepository;
import com.pedro.orso.softdesign.web.rest.dto.PautaDto;
import com.pedro.orso.softdesign.web.rest.dto.PautaDtoCriteria;
import com.pedro.orso.softdesign.web.rest.mapper.PautaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    /**
     * Creates a Pauta.
     *
     * @param pautaDto the entity to create.
     * @return the persisted entity.
     */
    @Transactional
    public PautaDto createPauta(PautaDto pautaDto) {

        if (pautaDto == null) {
            throw new PautaException.PautaIsInvalid();
        }

        log.info("Creating new pauta with title: {}", pautaDto.getTitle());

        var pauta = pautaMapper.toEntity(pautaDto);

        if (pautaDto.getTotalRunTime() == null) {
            pauta.setTotalRunTime(Duration.ofMinutes(1));
        }
        pauta.setId(null); // DB sequence
        pauta.setCreateDate(ZonedDateTime.now());
        pauta.setModificationDate(ZonedDateTime.now());

        Pauta savedPauta = pautaRepository.save(pauta);
        log.info("Pauta created successfully with id: {}", savedPauta.getId());

        return pautaMapper.toDto(savedPauta);
    }

    /**
     * Gets a Pauta by its id.
     *
     * @param id of the entity to search.
     * @return the entity.
     */
    public PautaDto getPautaById(Long id) {
        log.info("Searching for Pauta with id: {}", id);

        Pauta pauta = pautaRepository.findById(id).orElseThrow(PautaException.PautaDoesNotExistException::new);

        return pautaMapper.toDto(pauta);
    }

    /**
     * Gets a  list of Pautas by its search criteria.
     *
     * @param criteria list of filters to search.
     * @return the list of entities.
     */
    public Page<PautaDto> findByCriteria(PautaDtoCriteria criteria, Pageable page) {
        log.debug("Finding Pautas by criteria: {}, page: {}", criteria, page);

        Specification<Pauta> specification = Specification.where(null);

        if (criteria != null) {
            if (criteria.getId() != null && criteria.getId().getEquals() != null) {
                specification = specification.and(
                        (root, query, builder) -> builder.equal(root.get("id"), criteria.getId().getEquals())
                );
            }
            if (criteria.getTitle() != null && criteria.getTitle().getContains() != null) {
                specification = specification.and(
                        (root, query, builder) -> builder.like(root.get("title"), "%" + criteria.getTitle().getContains() + "%")
                );
            }
            if (criteria.getIsStarted() != null && criteria.getIsStarted().getEquals() != null) {
                specification = specification.and(
                        (root, query, builder) -> builder.equal(root.get("isOpen"), criteria.getIsStarted().getEquals())
                );
            }
        }

        return pautaRepository.findAll(specification, page)
                .map(pautaMapper::toDto);
    }

    /**
     * Deletes a Pauta by its id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void deletePautaById(Long id) {
        log.info("Pauta with id {} successfully deleted", id);

        pautaRepository.deleteById(id);
    }

    /**
     * Updates a Pauta by its id.
     *
     * @param id       of the entity.
     * @param pautaDto the entity to update.
     * @return the entity.
     */
    @Transactional
    public PautaDto updatePauta(Long id, PautaDto pautaDto) {
        log.info("Request to update pauta: {} {} ", id, pautaDto);

        Pauta pauta = pautaRepository.findById(id).orElseThrow(PautaException.PautaDoesNotExistException::new);

        // No edits for closed ones
        if (pauta.isClosed()) {
            throw new PautaException.PautaIsClosed();
        }

        // No edits for started ones
        if (pauta.isStarted()) {
            throw new PautaException.PautaIsStarted();
        }

        // Default 1 minute
        if (pautaDto.getTotalRunTime() == null) {
            pauta.setTotalRunTime(Duration.ofMinutes(1));
        } else {
            pauta.setTotalRunTime(pautaDto.getTotalRunTime());
        }

        // Start date
        if (!pauta.isStarted() && pautaDto.isStarted()) {
            pauta.setStartDate(ZonedDateTime.now());
        }

        // Data
        pauta.setTitle(pautaDto.getTitle());
        pauta.setStarted(pautaDto.isStarted());
        pauta.setModificationDate(ZonedDateTime.now());

        Pauta updatedPauta = pautaRepository.save(pauta);
        log.info("Pauta updated successfully with id: {}", updatedPauta.getId());

        return pautaMapper.toDto(updatedPauta);
    }

}
