package com.jhipster.persona.service.impl;

import com.jhipster.persona.service.DomicilioService;
import com.jhipster.persona.domain.Domicilio;
import com.jhipster.persona.repository.DomicilioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Domicilio}.
 */
@Service
@Transactional
public class DomicilioServiceImpl implements DomicilioService {

    private final Logger log = LoggerFactory.getLogger(DomicilioServiceImpl.class);

    private final DomicilioRepository domicilioRepository;

    public DomicilioServiceImpl(DomicilioRepository domicilioRepository) {
        this.domicilioRepository = domicilioRepository;
    }

    @Override
    public Domicilio save(Domicilio domicilio) {
        log.debug("Request to save Domicilio : {}", domicilio);
        return domicilioRepository.save(domicilio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Domicilio> findAll(Pageable pageable) {
        log.debug("Request to get all Domicilios");
        return domicilioRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Domicilio> findOne(Long id) {
        log.debug("Request to get Domicilio : {}", id);
        return domicilioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Domicilio : {}", id);
        domicilioRepository.deleteById(id);
    }
}
