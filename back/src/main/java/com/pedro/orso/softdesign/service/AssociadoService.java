package com.pedro.orso.softdesign.service;

import com.pedro.orso.softdesign.domain.Associado;
import com.pedro.orso.softdesign.domain.AssociadoStatus;
import com.pedro.orso.softdesign.exception.AssociadoException;
import com.pedro.orso.softdesign.repository.AssociadoRepository;
import com.pedro.orso.softdesign.web.rest.dto.AssociadoDto;
import com.pedro.orso.softdesign.web.rest.mapper.AssociadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    private final AssociadoMapper associadoMapper;

    public static String sanitizeToNumbersOnly(String input) {
        if (input == null) {
            return null;
        }
        // Replace all non-digit characters with an empty string
        return input.replaceAll("[^0-9]", "");
    }

    /**
     * Creates an Associado.
     *
     * @param associadoDto the entity to create.
     * @return the persisted entity.
     */
    @Transactional
    public AssociadoDto createAssociado(AssociadoDto associadoDto) {

        if (associadoDto == null || associadoDto.getCpf().isEmpty()) {
            throw new AssociadoException.AssociadoIsInvalid();
        }

        // Validate unique CPF
        String cleanCpf = sanitizeToNumbersOnly(associadoDto.getCpf());
        Optional<Associado> associadoCheck = associadoRepository.findByCpf(cleanCpf);
        if (associadoCheck.isPresent()) {
            throw new AssociadoException.AssociadoAlreadyExistException();
        }

        log.info("Creating new associado with cpf: {}", associadoDto.getCpf());

        var associado = associadoMapper.toEntity(associadoDto);

        if (associadoDto.getStatus() == null) {
            associadoDto.setStatus(AssociadoStatus.UNABLE_TO_VOTE);
        }
        associado.setId(null); // Sequence
        associado.setCreateDate(ZonedDateTime.now());
        associado.setModificationDate(ZonedDateTime.now());
        associado.setCpf(sanitizeToNumbersOnly(associado.getCpf()));

        Associado savedAssociado = associadoRepository.save(associado);
        log.info("Associado created successfully with id: {}", savedAssociado.getId());

        return associadoMapper.toDto(savedAssociado);
    }

    /**
     * Gets a Associado by its id.
     *
     * @param cpf of the entity to search.
     * @return the entity.
     */
    public AssociadoDto getAssociadoByCpf(String cpf) {
        log.info("Searching for Associado with cpf: {}", cpf);

        Associado associado = associadoRepository.findByCpf(cpf).orElseThrow(AssociadoException.AssociadoDoesNotExistException::new);

        return associadoMapper.toDto(associado);
    }

    /**
     * Gets a  list of associados.
     *
     * @return the list of entities.
     */
    public Page<AssociadoDto> findByCriteria(Pageable page) {
        log.debug("Finding Associados, page: {}", page);

        Specification<Associado> specification = Specification.where(null);

        return associadoRepository.findAll(specification, page).map(associadoMapper::toDto);
    }

}
