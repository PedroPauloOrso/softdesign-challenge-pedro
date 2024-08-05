package com.pedro.orso.softdesign.repository;

import com.pedro.orso.softdesign.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>, JpaSpecificationExecutor<Pauta> {

}
