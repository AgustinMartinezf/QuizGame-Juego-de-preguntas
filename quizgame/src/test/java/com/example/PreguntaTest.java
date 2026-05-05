package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.Pregunta;

public class PreguntaTest {
    private Pregunta pregunta;

    @Before
    public void setUp() {
        pregunta = new Pregunta(
                1,
                "Capital de Francia",
                new String[]{"Paris", "Madrid", "Roma"},
                "Paris",
                "Geografia"
        );
    }

    @Test
    public void testConstructor() {
        assertEquals(1, pregunta.getId());
        assertEquals("Capital de Francia", pregunta.getEnunciado());
        assertEquals("Paris", pregunta.getRespuestaCorrecta());
        assertEquals("Geografia", pregunta.getCategoria());
    }

    @Test
    public void testGetOpciones() {
        assertArrayEquals(new String[]{"Paris", "Madrid", "Roma"}, pregunta.getOpciones());
    }

    @Test
    public void testEsCorrectaTrue() {
        assertTrue(pregunta.esCorrecta("Paris"));
    }

    @Test
    public void testEsCorrectaIgnoraMayusculas() {
        assertTrue(pregunta.esCorrecta("paris"));
        assertTrue(pregunta.esCorrecta("PARIS"));
    }

    @Test
    public void testEsCorrectaFalse() {
        assertFalse(pregunta.esCorrecta("Madrid"));
    }

    @Test
    public void testEsCorrectaConNullEsFalse() {
        assertFalse(pregunta.esCorrecta(null));
    }

    @Test
    public void testEnunciadoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta(2, "  ", new String[]{"a", "b"}, "a", "Cat"));
    }

    @Test
    public void testMenosDeDosOpcionesLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta(2, "E", new String[]{"a"}, "a", "Cat"));
    }
}
