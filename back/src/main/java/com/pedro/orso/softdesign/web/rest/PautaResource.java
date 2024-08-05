package com.pedro.orso.softdesign.web.rest;

import com.pedro.orso.softdesign.service.PautaService;
import com.pedro.orso.softdesign.web.rest.dto.PautaDto;
import com.pedro.orso.softdesign.web.rest.dto.PautaDtoCriteria;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@Tag(name = "Pauta", description = "Pauta Resource")
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class PautaResource {

    private static final String PAUTA_URI = "/v1/pautas";

    private final PautaService pautaService;

    /**
     * {@code POST  /v1/pautas} : Create a new pauta.
     *
     * @param pautaDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Pauta,
     */
    @ApiOperation(value = "Create a new pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a pauta"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(PAUTA_URI)
    public ResponseEntity<PautaDto> createPauta(@Valid @RequestBody PautaDto pautaDto) {
        log.info("Request to create pauta: {}", pautaDto);

        PautaDto result = pautaService.createPauta(pautaDto);
        URI location = URI.create(String.format("/api/v1/pautas/%s", result.getId()));

        log.info("Pauta created successfully with id: {}", result.getId());

        return ResponseEntity.created(location).body(result);
    }

    /**
     * {@code GET  /v1/pautas/:id} : get the pauta by id.
     *
     * @param id the id of the pauta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)},
     * or with status {@code 404 (Not Found)}.
     */
    @ApiOperation(value = "Retrieve a pauta by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pauta found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PautaDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "pauta not found", content = @Content)
    })
    @GetMapping(PAUTA_URI + "/{id}")
    public ResponseEntity<PautaDto> getPauta(
            @Parameter(description = "id of the pauta")
            @PathVariable Long id
    ) {
        PautaDto pautaDto = pautaService.getPautaById(id);
        return ResponseEntity.ok(pautaDto);
    }

    /**
     * {@code GET  /v1/pautas} : get all the pautas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @ApiOperation(value = "Returns a list of pautas by search criteria")
    @GetMapping(PAUTA_URI)
    public ResponseEntity<List<PautaDto>> getPautas(
            @ParameterObject PautaDtoCriteria criteria,
            @Parameter(hidden = true)
            @SortDefault(sort = "createDate", direction = Sort.Direction.ASC)
            @PageableDefault(size = 200) Pageable pageable
    ) {
        log.debug("REST request to get pautas by criteria: {}", criteria);

        var result = pautaService.findByCriteria(criteria, pageable);

        return ResponseEntity.ok(result.getContent());
    }

    /**
     * {@code PUT  /v1/pautas/:id} : Updates an existing pauta.
     *
     * @param id       the id of the pauta to update.
     * @param pautaDto the PautaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pautaDto,
     * or with status {@code 400 (Bad Request)} if the pautaDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pautaDto couldn't be updated.
     */
    @ApiOperation(value = "Updates a pauta by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pauta updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "pauta not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "pauta uses a descriptionKey already being used", content = @Content)
    })
    @PutMapping(PAUTA_URI + "/{id}")
    public ResponseEntity<PautaDto> updatePauta(
            @Parameter(description = "id of the pauta")
            @PathVariable Long id,
            @Valid @RequestBody PautaDto pautaDto
    ) {
        log.debug("REST request to update a pauta: {} {}", id, pautaDto);

        PautaDto newPautaDto = pautaService.updatePauta(id, pautaDto);
        return ResponseEntity.ok(newPautaDto);
    }

    /**
     * {@code DELETE  /v1/pautas/:id} : delete the pauta by id.
     *
     * @param id the id of the pauta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)} or {@code 404 (NOT_FOUND)}.
     */
    @ApiOperation(value = "Deletes a pauta by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "pauta deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "pauta not found", content = @Content)
    })
    @DeleteMapping(PAUTA_URI + "/{id}")
    public ResponseEntity<Void> deletePauta(
            @Parameter(description = "id of the pauta to be deleted", required = true)
            @PathVariable Long id
    ) {
        log.debug("REST request to delete a pauta: {}", id);

        var pautaDto = pautaService.getPautaById(id);
        if (pautaDto == null) {
            log.warn("pauta with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        pautaService.deletePautaById(id);

        return ResponseEntity.noContent().build();
    }

}
