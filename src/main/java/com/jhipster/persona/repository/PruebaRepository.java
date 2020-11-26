package com.jhipster.persona.repository;

import com.jhipster.persona.domain.Prueba;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Prueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {
}
