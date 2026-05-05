package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.Pregunta;
import com.Respuesta;

public class RespuestaTest {

    private Pregunta nuevaPregunta() {
        return new Pregunta(1, "Capital de Uruguay",
                new String[]{"Buenos Aires", "Montevideo"}, "Montevideo", "Geo");
    }

    @Test
    public void respuestaCorrectaOtorgaPuntos() {
        Respuesta r = new Respuesta(nuevaPregunta(), "Montevideo", 10);
        assertTrue(r.isCorrecta());
        assertEquals(10, r.getPuntosOtorgados());
    }

    @Test
    public void respuestaIncorrectaCeroPuntos() {
        Respuesta r = new Respuesta(nuevaPregunta(), "Buenos Aires", 10);
        assertFalse(r.isCorrecta());
        assertEquals(0, r.getPuntosOtorgados());
    }

    @Test
    public void conservaPreguntaYRespuestaDada() {
        Respuesta r = new Respuesta(nuevaPregunta(), "Montevideo", 10);
        assertEquals(1, r.getPregunta().getId());
        assertEquals("Montevideo", r.getRespuestaDada());
    }

    @Test
    public void respuestaCaseInsensitiveEsCorrecta() {
        Respuesta r = new Respuesta(nuevaPregunta(), "montevideo", 10);
        assertTrue(r.isCorrecta());
        assertEquals(10, r.getPuntosOtorgados());
    }
}
