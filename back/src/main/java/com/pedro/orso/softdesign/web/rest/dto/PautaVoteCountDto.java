package com.pedro.orso.softdesign.web.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the Pauta and Vote result entity.
 */
@Data
@NoArgsConstructor
public class PautaVoteCountDto {

    private Long totalVotes;

    private Long totalYesVotes;

    private Long totalNoVotes;

    public PautaVoteCountDto(Long totalVotes, Long totalYesVotes, Long totalNoVotes) {
        this.totalVotes = totalVotes;
        this.totalYesVotes = totalYesVotes;
        this.totalNoVotes = totalNoVotes;
    }

}
