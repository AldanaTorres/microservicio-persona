package com.jhipster.persona.web.rest;

import com.jhipster.persona.PersonaApp;
import com.jhipster.persona.domain.Prueba;
import com.jhipster.persona.repository.PruebaRepository;
import com.jhipster.persona.service.PruebaService;

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
 * Integration tests for the {@link PruebaResource} REST controller.
 */
@SpringBootTest(classes = PersonaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PruebaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private PruebaService pruebaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPruebaMockMvc;

    private Prueba prueba;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prueba createEntity(EntityManager em) {
        Prueba prueba = new Prueba()
            .nombre(DEFAULT_NOMBRE);
        return prueba;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prueba createUpdatedEntity(EntityManager em) {
        Prueba prueba = new Prueba()
            .nombre(UPDATED_NOMBRE);
        return prueba;
    }

    @BeforeEach
    public void initTest() {
        prueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrueba() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();
        // Create the Prueba
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isCreated());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba with an existing ID
        prueba.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPruebas() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);

        // Get all the pruebaList
        restPruebaMockMvc.perform(get("/api/pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prueba.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getPrueba() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);

        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prueba.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }
    @Test
    @Transactional
    public void getNonExistingPrueba() throws Exception {
        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);

        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Update the prueba
        Prueba updatedPrueba = pruebaRepository.findById(prueba.getId()).get();
        // Disconnect from session so that the updates on updatedPrueba are not directly saved in db
        em.detach(updatedPrueba);
        updatedPrueba
            .nombre(UPDATED_NOMBRE);

        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrueba)))
            .andExpect(status().isOk());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrueba() throws Exception {
        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);

        int databaseSizeBeforeDelete = pruebaRepository.findAll().size();

        // Delete the prueba
        restPruebaMockMvc.perform(delete("/api/pruebas/{id}", prueba.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
