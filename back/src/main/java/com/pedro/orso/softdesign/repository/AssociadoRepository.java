package com.pedro.orso.softdesign.repository;

import com.pedro.orso.softdesign.domain.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long>, JpaSpecificationExecutor<Associado> {

    Optional<Associado> findByCpf(String cpf);
}
