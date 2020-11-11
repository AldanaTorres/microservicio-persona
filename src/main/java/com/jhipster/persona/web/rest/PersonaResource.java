package com.jhipster.persona.web.rest;

import com.jhipster.persona.domain.Persona;
import com.jhipster.persona.service.PersonaService;
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
 * REST controller for managing {@link com.jhipster.persona.domain.Persona}.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/personas")
public class PersonaResource {

    private final Logger log = LoggerFactory.getLogger(PersonaResource.class);

    private static final String ENTITY_NAME = "personaPersona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonaService personaService;

    public PersonaResource(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * {@code POST  /personas} : Create a new persona.
     *
     * @param persona the persona to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new persona, or with status {@code 400 (Bad Request)} if the persona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personas")
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) throws URISyntaxException {
        log.debug("REST request to save Persona : {}", persona);
        if (persona.getId() != null) {
            throw new BadRequestAlertException("A new persona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Persona result = personaService.save(persona);
        return ResponseEntity.created(new URI("/api/personas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personas} : Updates an existing persona.
     *
     * @param persona the persona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persona,
     * or with status {@code 400 (Bad Request)} if the persona is not valid,
     * or with status {@code 500 (Internal Server Error)} if the persona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personas")
    public ResponseEntity<Persona> updatePersona(@RequestBody Persona persona) throws URISyntaxException {
        log.debug("REST request to update Persona : {}", persona);
        if (persona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Persona result = personaService.save(persona);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persona.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personas} : get all the personas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personas in body.
     */
    @GetMapping("/personas")
    public ResponseEntity<List<Persona>> getAllPersonas(Pageable pageable) {
        log.debug("REST request to get a page of Personas");
        Page<Persona> page = personaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personas/:id} : get the "id" persona.
     *
     * @param id the id of the persona to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the persona, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersona(@PathVariable Long id) {
        log.debug("REST request to get Persona : {}", id);
        Optional<Persona> persona = personaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(persona);
    }

    /**
     * {@code DELETE  /personas/:id} : delete the "id" persona.
     *
     * @param id the id of the persona to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        log.debug("REST request to delete Persona : {}", id);
        personaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     *
     * @param filtro
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String filtro){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personaService.search(filtro));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \""+ e.getMessage() + "\"}"));
        }
    }

    /**
     *
     * @param filtro
     * @param pageable
     * @return
     */
    @GetMapping("/searchPaged")
    public ResponseEntity<?> search(@RequestParam String filtro, Pageable pageable){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personaService.search(filtro, pageable));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \""+ e.getMessage() + "\"}"));
        }
    }
}
