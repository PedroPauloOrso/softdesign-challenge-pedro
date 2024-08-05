package com.pedro.orso.softdesign.repository;

import com.pedro.orso.softdesign.domain.Voto;
import com.pedro.orso.softdesign.web.rest.dto.PautaVoteCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>, JpaSpecificationExecutor<Voto> {

    Optional<Voto> findByAssociadoIdAndPautaId(Long id, Long id1);

    @Query("SELECT new com.pedro.orso.softdesign.web.rest.dto.PautaVoteCountDto(" +
            "COUNT(v), " +
            "SUM(CASE WHEN v.voteOption = TRUE THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN v.voteOption = FALSE THEN 1 ELSE 0 END)) " +
            "FROM Voto v WHERE v.pauta.id = :pautaId")
    PautaVoteCountDto getVotoCountByPautaId(@Param("pautaId") Long pautaId);
}
