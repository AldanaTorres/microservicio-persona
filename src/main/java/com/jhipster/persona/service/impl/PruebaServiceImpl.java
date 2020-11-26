package com.jhipster.persona.service.impl;

import com.jhipster.persona.service.PruebaService;
import com.jhipster.persona.domain.Prueba;
import com.jhipster.persona.repository.PruebaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prueba}.
 */
@Service
@Transactional
public class PruebaServiceImpl implements PruebaService {

    private final Logger log = LoggerFactory.getLogger(PruebaServiceImpl.class);

    private final PruebaRepository pruebaRepository;

    public PruebaServiceImpl(PruebaRepository pruebaRepository) {
        this.pruebaRepository = pruebaRepository;
    }

    @Override
    public Prueba save(Prueba prueba) {
        log.debug("Request to save Prueba : {}", prueba);
        return pruebaRepository.save(prueba);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prueba> findAll(Pageable pageable) {
        log.debug("Request to get all Pruebas");
        return pruebaRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Prueba> findOne(Long id) {
        log.debug("Request to get Prueba : {}", id);
        return pruebaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prueba : {}", id);
        pruebaRepository.deleteById(id);
    }
}
