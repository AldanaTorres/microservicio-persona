package com.jhipster.persona.web.rest;

import com.jhipster.persona.domain.Domicilio;
import com.jhipster.persona.service.DomicilioService;
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
 * REST controller for managing {@link com.jhipster.persona.domain.Domicilio}.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/domicilios")
public class DomicilioResource {

    private final Logger log = LoggerFactory.getLogger(DomicilioResource.class);

    private static final String ENTITY_NAME = "personaDomicilio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomicilioService domicilioService;

    public DomicilioResource(DomicilioService domicilioService) {
        this.domicilioService = domicilioService;
    }

    /**
     * {@code POST  /domicilios} : Create a new domicilio.
     *
     * @param domicilio the domicilio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domicilio, or with status {@code 400 (Bad Request)} if the domicilio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domicilios")
    public ResponseEntity<Domicilio> createDomicilio(@RequestBody Domicilio domicilio) throws URISyntaxException {
        log.debug("REST request to save Domicilio : {}", domicilio);
        if (domicilio.getId() != null) {
            throw new BadRequestAlertException("A new domicilio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Domicilio result = domicilioService.save(domicilio);
        return ResponseEntity.created(new URI("/api/domicilios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /domicilios} : Updates an existing domicilio.
     *
     * @param domicilio the domicilio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domicilio,
     * or with status {@code 400 (Bad Request)} if the domicilio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domicilio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domicilios")
    public ResponseEntity<Domicilio> updateDomicilio(@RequestBody Domicilio domicilio) throws URISyntaxException {
        log.debug("REST request to update Domicilio : {}", domicilio);
        if (domicilio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Domicilio result = domicilioService.save(domicilio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domicilio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /domicilios} : get all the domicilios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domicilios in body.
     */
    @GetMapping("/domicilios")
    public ResponseEntity<List<Domicilio>> getAllDomicilios(Pageable pageable) {
        log.debug("REST request to get a page of Domicilios");
        Page<Domicilio> page = domicilioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /domicilios/:id} : get the "id" domicilio.
     *
     * @param id the id of the domicilio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domicilio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domicilios/{id}")
    public ResponseEntity<Domicilio> getDomicilio(@PathVariable Long id) {
        log.debug("REST request to get Domicilio : {}", id);
        Optional<Domicilio> domicilio = domicilioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domicilio);
    }

    /**
     * {@code DELETE  /domicilios/:id} : delete the "id" domicilio.
     *
     * @param id the id of the domicilio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domicilios/{id}")
    public ResponseEntity<Void> deleteDomicilio(@PathVariable Long id) {
        log.debug("REST request to delete Domicilio : {}", id);
        domicilioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
