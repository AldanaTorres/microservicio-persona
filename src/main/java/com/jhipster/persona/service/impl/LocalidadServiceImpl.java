package com.jhipster.persona.service.impl;

import com.jhipster.persona.service.LocalidadService;
import com.jhipster.persona.domain.Localidad;
import com.jhipster.persona.repository.LocalidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Localidad}.
 */
@Service
@Transactional
public class LocalidadServiceImpl implements LocalidadService {

    private final Logger log = LoggerFactory.getLogger(LocalidadServiceImpl.class);

    private final LocalidadRepository localidadRepository;

    public LocalidadServiceImpl(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    @Override
    public Localidad save(Localidad localidad) {
        log.debug("Request to save Localidad : {}", localidad);
        return localidadRepository.save(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Localidad> findAll(Pageable pageable) {
        log.debug("Request to get all Localidads");
        return localidadRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Localidad> findOne(Long id) {
        log.debug("Request to get Localidad : {}", id);
        return localidadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localidad : {}", id);
        localidadRepository.deleteById(id);
    }
}
