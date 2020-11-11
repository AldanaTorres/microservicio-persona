package com.jhipster.persona.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.persona.web.rest.TestUtil;

public class DomicilioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domicilio.class);
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setId(1L);
        Domicilio domicilio2 = new Domicilio();
        domicilio2.setId(domicilio1.getId());
        assertThat(domicilio1).isEqualTo(domicilio2);
        domicilio2.setId(2L);
        assertThat(domicilio1).isNotEqualTo(domicilio2);
        domicilio1.setId(null);
        assertThat(domicilio1).isNotEqualTo(domicilio2);
    }
}
