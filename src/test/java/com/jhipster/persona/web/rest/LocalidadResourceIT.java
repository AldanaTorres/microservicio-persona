package com.jhipster.persona.web.rest;

import com.jhipster.persona.PersonaApp;
import com.jhipster.persona.domain.Localidad;
import com.jhipster.persona.repository.LocalidadRepository;
import com.jhipster.persona.service.LocalidadService;

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
 * Integration tests for the {@link LocalidadResource} REST controller.
 */
@SpringBootTest(classes = PersonaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocalidadResourceIT {

    private static final String DEFAULT_DENOMINACION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINACION = "BBBBBBBBBB";

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalidadMockMvc;

    private Localidad localidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localidad createEntity(EntityManager em) {
        Localidad localidad = new Localidad()
            .denominacion(DEFAULT_DENOMINACION);
        return localidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localidad createUpdatedEntity(EntityManager em) {
        Localidad localidad = new Localidad()
            .denominacion(UPDATED_DENOMINACION);
        return localidad;
    }

    @BeforeEach
    public void initTest() {
        localidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidad() throws Exception {
        int databaseSizeBeforeCreate = localidadRepository.findAll().size();
        // Create the Localidad
        restLocalidadMockMvc.perform(post("/api/localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localidad)))
            .andExpect(status().isCreated());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeCreate + 1);
        Localidad testLocalidad = localidadList.get(localidadList.size() - 1);
        assertThat(testLocalidad.getDenominacion()).isEqualTo(DEFAULT_DENOMINACION);
    }

    @Test
    @Transactional
    public void createLocalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidadRepository.findAll().size();

        // Create the Localidad with an existing ID
        localidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidadMockMvc.perform(post("/api/localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localidad)))
            .andExpect(status().isBadRequest());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocalidads() throws Exception {
        // Initialize the database
        localidadRepository.saveAndFlush(localidad);

        // Get all the localidadList
        restLocalidadMockMvc.perform(get("/api/localidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].denominacion").value(hasItem(DEFAULT_DENOMINACION)));
    }
    
    @Test
    @Transactional
    public void getLocalidad() throws Exception {
        // Initialize the database
        localidadRepository.saveAndFlush(localidad);

        // Get the localidad
        restLocalidadMockMvc.perform(get("/api/localidads/{id}", localidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localidad.getId().intValue()))
            .andExpect(jsonPath("$.denominacion").value(DEFAULT_DENOMINACION));
    }
    @Test
    @Transactional
    public void getNonExistingLocalidad() throws Exception {
        // Get the localidad
        restLocalidadMockMvc.perform(get("/api/localidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidad() throws Exception {
        // Initialize the database
        localidadService.save(localidad);

        int databaseSizeBeforeUpdate = localidadRepository.findAll().size();

        // Update the localidad
        Localidad updatedLocalidad = localidadRepository.findById(localidad.getId()).get();
        // Disconnect from session so that the updates on updatedLocalidad are not directly saved in db
        em.detach(updatedLocalidad);
        updatedLocalidad
            .denominacion(UPDATED_DENOMINACION);

        restLocalidadMockMvc.perform(put("/api/localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalidad)))
            .andExpect(status().isOk());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeUpdate);
        Localidad testLocalidad = localidadList.get(localidadList.size() - 1);
        assertThat(testLocalidad.getDenominacion()).isEqualTo(UPDATED_DENOMINACION);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidad() throws Exception {
        int databaseSizeBeforeUpdate = localidadRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalidadMockMvc.perform(put("/api/localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localidad)))
            .andExpect(status().isBadRequest());

        // Validate the Localidad in the database
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalidad() throws Exception {
        // Initialize the database
        localidadService.save(localidad);

        int databaseSizeBeforeDelete = localidadRepository.findAll().size();

        // Delete the localidad
        restLocalidadMockMvc.perform(delete("/api/localidads/{id}", localidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localidad> localidadList = localidadRepository.findAll();
        assertThat(localidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
