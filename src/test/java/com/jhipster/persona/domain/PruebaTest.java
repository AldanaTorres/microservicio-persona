package com.jhipster.persona.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.persona.web.rest.TestUtil;

public class PruebaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prueba.class);
        Prueba prueba1 = new Prueba();
        prueba1.setId(1L);
        Prueba prueba2 = new Prueba();
        prueba2.setId(prueba1.getId());
        assertThat(prueba1).isEqualTo(prueba2);
        prueba2.setId(2L);
        assertThat(prueba1).isNotEqualTo(prueba2);
        prueba1.setId(null);
        assertThat(prueba1).isNotEqualTo(prueba2);
    }
}
