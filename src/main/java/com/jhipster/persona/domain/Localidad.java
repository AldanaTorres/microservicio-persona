package com.jhipster.persona.domain;


import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;

import java.io.Serializable;

/**
 * A Localidad.
 */
@Entity
@Table(name = "localidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Localidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "denominacion")
    private String denominacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public Localidad denominacion(String denominacion) {
        this.denominacion = denominacion;
        return this;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localidad)) {
            return false;
        }
        return id != null && id.equals(((Localidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Localidad{" +
            "id=" + getId() +
            ", denominacion='" + getDenominacion() + "'" +
            "}";
    }
}
