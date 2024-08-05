package com.pedro.orso.softdesign.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PautaTest {

    @Test
    public void testPautaConstructor() {
        // Create a Pauta object
        Pauta pauta = new Pauta();

        assertNotNull(pauta);
        assertNull(pauta.getId());
        assertNull(pauta.getTitle());
        assertNull(pauta.getTotalRunTime());
        assertNull(pauta.getStartDate());
        assertNull(pauta.getCreateDate());
        assertNull(pauta.getModificationDate());
    }

}
