package com.pedro.orso.softdesign.web.rest;

import com.pedro.orso.softdesign.service.PautaService;
import com.pedro.orso.softdesign.service.VotoService;
import com.pedro.orso.softdesign.web.rest.dto.PautaDto;
import com.pedro.orso.softdesign.web.rest.dto.PautaDtoCriteria;
import com.pedro.orso.softdesign.web.rest.dto.PautaVoteCountDto;
import com.pedro.orso.softdesign.web.rest.dto.VotoDto;
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


@Tag(name = "Voto", description = "Voto Resource")
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class VotoResource {

    private static final String VOTO_URI = "/v1/votos";

    private final VotoService votoService;

    /**
     * {@code POST  /v1/votos} : Create a new Voto.
     *
     * @param votoDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Voto,
     */
    @ApiOperation(value = "Create a new Voto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a Voto"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(VOTO_URI)
    public ResponseEntity<VotoDto> createVoto(@Valid @RequestBody VotoDto votoDto) {
        log.info("Request to create voto: {}", votoDto);

        VotoDto result = votoService.createVoto(votoDto);
        URI location = URI.create(String.format("/api/v1/votos/%s", result.getId()));

        log.info("Voto created successfully with id: {}", result.getId());

        return ResponseEntity.created(location).body(result);
    }

    /**
     * {@code GET  /v1/votos/:id} : get the pauta by id.
     *
     * @param pautaId the id of the pauta to retrieve.
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
    @GetMapping(VOTO_URI + "/{pautaId}")
    public ResponseEntity<PautaVoteCountDto> getPautaVoteCount(
            @Parameter(description = "id of the pauta")
            @PathVariable Long pautaId
    ) {
        PautaVoteCountDto result = votoService.getPautaVoteCount(pautaId);
        return ResponseEntity.ok(result);
    }



}
