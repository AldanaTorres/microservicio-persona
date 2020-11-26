package com.jhipster.persona.web.rest;

import com.jhipster.persona.domain.Prueba;
import com.jhipster.persona.service.PruebaService;
import com.jhipster.persona.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jhipster.persona.domain.Prueba}.
 */
@RestController
@RequestMapping("/api")
public class PruebaResource {

    private final Logger log = LoggerFactory.getLogger(PruebaResource.class);

    private static final String ENTITY_NAME = "personaPrueba";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PruebaService pruebaService;

    public PruebaResource(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    /**
     * {@code POST  /pruebas} : Create a new prueba.
     *
     * @param prueba the prueba to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prueba, or with status {@code 400 (Bad Request)} if the prueba has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pruebas")
    public ResponseEntity<Prueba> createPrueba(@RequestBody Prueba prueba) throws URISyntaxException {
        log.debug("REST request to save Prueba : {}", prueba);
        if (prueba.getId() != null) {
            throw new BadRequestAlertException("A new prueba cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prueba result = pruebaService.save(prueba);
        return ResponseEntity.created(new URI("/api/pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pruebas} : Updates an existing prueba.
     *
     * @param prueba the prueba to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prueba,
     * or with status {@code 400 (Bad Request)} if the prueba is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prueba couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pruebas")
    public ResponseEntity<Prueba> updatePrueba(@RequestBody Prueba prueba) throws URISyntaxException {
        log.debug("REST request to update Prueba : {}", prueba);
        if (prueba.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prueba result = pruebaService.save(prueba);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prueba.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pruebas} : get all the pruebas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pruebas in body.
     */
    @GetMapping("/pruebas")
    public ResponseEntity<List<Prueba>> getAllPruebas(Pageable pageable) {
        log.debug("REST request to get a page of Pruebas");
        Page<Prueba> page = pruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pruebas/:id} : get the "id" prueba.
     *
     * @param id the id of the prueba to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prueba, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pruebas/{id}")
    public ResponseEntity<Prueba> getPrueba(@PathVariable Long id) {
        log.debug("REST request to get Prueba : {}", id);
        Optional<Prueba> prueba = pruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prueba);
    }

    /**
     * {@code DELETE  /pruebas/:id} : delete the "id" prueba.
     *
     * @param id the id of the prueba to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pruebas/{id}")
    public ResponseEntity<Void> deletePrueba(@PathVariable Long id) {
        log.debug("REST request to delete Prueba : {}", id);
        pruebaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
