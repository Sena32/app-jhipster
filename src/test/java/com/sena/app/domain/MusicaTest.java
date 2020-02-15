package com.sena.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sena.app.web.rest.TestUtil;

public class MusicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Musica.class);
        Musica musica1 = new Musica();
        musica1.setId(1L);
        Musica musica2 = new Musica();
        musica2.setId(musica1.getId());
        assertThat(musica1).isEqualTo(musica2);
        musica2.setId(2L);
        assertThat(musica1).isNotEqualTo(musica2);
        musica1.setId(null);
        assertThat(musica1).isNotEqualTo(musica2);
    }
}
