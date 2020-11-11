package com.jhipster.persona.service.impl;

import com.jhipster.persona.service.LibroService;
import com.jhipster.persona.domain.Libro;
import com.jhipster.persona.repository.LibroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Libro}.
 */
@Service
@Transactional
public class LibroServiceImpl implements LibroService {

    private final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);

    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public Libro save(Libro libro) {
        log.debug("Request to save Libro : {}", libro);
        return libroRepository.save(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> findAll(Pageable pageable) {
        log.debug("Request to get all Libros");
        return libroRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> findOne(Long id) {
        log.debug("Request to get Libro : {}", id);
        return libroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Libro : {}", id);
        libroRepository.deleteById(id);
    }
}
