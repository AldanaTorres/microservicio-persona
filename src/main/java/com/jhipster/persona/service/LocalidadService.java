package com.jhipster.persona.service;

import com.jhipster.persona.domain.Localidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Localidad}.
 */
public interface LocalidadService {

    /**
     * Save a localidad.
     *
     * @param localidad the entity to save.
     * @return the persisted entity.
     */
    Localidad save(Localidad localidad);

    /**
     * Get all the localidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Localidad> findAll(Pageable pageable);


    /**
     * Get the "id" localidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Localidad> findOne(Long id);

    /**
     * Delete the "id" localidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
