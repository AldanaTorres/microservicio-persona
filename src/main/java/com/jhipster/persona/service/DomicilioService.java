package com.jhipster.persona.service;

import com.jhipster.persona.domain.Domicilio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Domicilio}.
 */
public interface DomicilioService {

    /**
     * Save a domicilio.
     *
     * @param domicilio the entity to save.
     * @return the persisted entity.
     */
    Domicilio save(Domicilio domicilio);

    /**
     * Get all the domicilios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Domicilio> findAll(Pageable pageable);


    /**
     * Get the "id" domicilio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Domicilio> findOne(Long id);

    /**
     * Delete the "id" domicilio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
