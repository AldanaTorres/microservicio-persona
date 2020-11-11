package com.jhipster.persona.web.rest;

import com.jhipster.persona.PersonaApp;
import com.jhipster.persona.domain.Domicilio;
import com.jhipster.persona.repository.DomicilioRepository;
import com.jhipster.persona.service.DomicilioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DomicilioResource} REST controller.
 */
@SpringBootTest(classes = PersonaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DomicilioResourceIT {

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDomicilioMockMvc;

    private Domicilio domicilio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domicilio createEntity(EntityManager em) {
        Domicilio domicilio = new Domicilio()
            .calle(DEFAULT_CALLE)
            .numero(DEFAULT_NUMERO);
        return domicilio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domicilio createUpdatedEntity(EntityManager em) {
        Domicilio domicilio = new Domicilio()
            .calle(UPDATED_CALLE)
            .numero(UPDATED_NUMERO);
        return domicilio;
    }

    @BeforeEach
    public void initTest() {
        domicilio = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomicilio() throws Exception {
        int databaseSizeBeforeCreate = domicilioRepository.findAll().size();
        // Create the Domicilio
        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilio)))
            .andExpect(status().isCreated());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeCreate + 1);
        Domicilio testDomicilio = domicilioList.get(domicilioList.size() - 1);
        assertThat(testDomicilio.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDomicilio.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createDomicilioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domicilioRepository.findAll().size();

        // Create the Domicilio with an existing ID
        domicilio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilio)))
            .andExpect(status().isBadRequest());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDomicilios() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList
        restDomicilioMockMvc.perform(get("/api/domicilios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilio.getId().intValue())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get the domicilio
        restDomicilioMockMvc.perform(get("/api/domicilios/{id}", domicilio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(domicilio.getId().intValue()))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }
    @Test
    @Transactional
    public void getNonExistingDomicilio() throws Exception {
        // Get the domicilio
        restDomicilioMockMvc.perform(get("/api/domicilios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomicilio() throws Exception {
        // Initialize the database
        domicilioService.save(domicilio);

        int databaseSizeBeforeUpdate = domicilioRepository.findAll().size();

        // Update the domicilio
        Domicilio updatedDomicilio = domicilioRepository.findById(domicilio.getId()).get();
        // Disconnect from session so that the updates on updatedDomicilio are not directly saved in db
        em.detach(updatedDomicilio);
        updatedDomicilio
            .calle(UPDATED_CALLE)
            .numero(UPDATED_NUMERO);

        restDomicilioMockMvc.perform(put("/api/domicilios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDomicilio)))
            .andExpect(status().isOk());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeUpdate);
        Domicilio testDomicilio = domicilioList.get(domicilioList.size() - 1);
        assertThat(testDomicilio.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDomicilio.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingDomicilio() throws Exception {
        int databaseSizeBeforeUpdate = domicilioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomicilioMockMvc.perform(put("/api/domicilios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilio)))
            .andExpect(status().isBadRequest());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomicilio() throws Exception {
        // Initialize the database
        domicilioService.save(domicilio);

        int databaseSizeBeforeDelete = domicilioRepository.findAll().size();

        // Delete the domicilio
        restDomicilioMockMvc.perform(delete("/api/domicilios/{id}", domicilio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
