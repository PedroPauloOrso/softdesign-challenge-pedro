package com.pedro.orso.softdesign.web.rest;

import com.pedro.orso.softdesign.service.AssociadoService;
import com.pedro.orso.softdesign.web.rest.dto.AssociadoDto;
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


@Tag(name = "Associado", description = "Associado Resource")
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class AssociadoResource {

    private static final String Associado_URI = "/v1/associados";

    private final AssociadoService associadoService;

    /**
     * {@code POST  /v1/associados} : Create a new associado.
     *
     * @param associadoDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Associado,
     */
    @ApiOperation(value = "Create a new associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created an associado"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(Associado_URI)
    public ResponseEntity<AssociadoDto> createAssociado(@Valid @RequestBody AssociadoDto associadoDto) {
        log.info("Request to create associado: {}", associadoDto);

        AssociadoDto result = associadoService.createAssociado(associadoDto);
        URI location = URI.create(String.format("/api/v1/associados/%s", result.getId()));

        log.info("Associado created successfully with id: {}", result.getId());

        return ResponseEntity.created(location).body(result);
    }

    /**
     * {@code GET  /v1/associados/:cpf} : get the associado by cpf.
     *
     * @param cpf the id of the associado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)},
     * or with status {@code 404 (Not Found)}.
     */
    @ApiOperation(value = "Retrieve an associado by its cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "associado found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AssociadoDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "pauta not found", content = @Content)
    })
    @GetMapping(Associado_URI + "/{cpf}")
    public ResponseEntity<AssociadoDto> getAssociado(
            @Parameter(description = "id of the Associado")
            @PathVariable String cpf
    ) {
        AssociadoDto associadoDto = associadoService.getAssociadoByCpf(cpf);
        return ResponseEntity.ok(associadoDto);
    }

    /**
     * {@code GET  /v1/associados} : get all the associados.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @ApiOperation(value = "Returns a list of associados by search criteria")
    @GetMapping(Associado_URI)
    public ResponseEntity<List<AssociadoDto>> getAssociados(
            @ParameterObject PautaDtoCriteria criteria,
            @Parameter(hidden = true)
            @SortDefault(sort = "createDate", direction = Sort.Direction.ASC)
            @PageableDefault(size = 200) Pageable pageable
    ) {
        log.debug("REST request to get pautas by criteria: {}", criteria);

        var result = associadoService.findByCriteria(pageable);

        return ResponseEntity.ok(result.getContent());
    }


}
