package com.jhipster.persona.service;

import com.jhipster.persona.domain.Prueba;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Prueba}.
 */
public interface PruebaService {

    /**
     * Save a prueba.
     *
     * @param prueba the entity to save.
     * @return the persisted entity.
     */
    Prueba save(Prueba prueba);

    /**
     * Get all the pruebas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prueba> findAll(Pageable pageable);


    /**
     * Get the "id" prueba.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prueba> findOne(Long id);

    /**
     * Delete the "id" prueba.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
