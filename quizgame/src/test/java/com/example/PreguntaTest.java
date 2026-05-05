package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.Pregunta;

public class PreguntaTest {
    private Pregunta pregunta;
    private String[] opciones;

    @Before
    public void setUp() {
        opciones = new String[]{" Paris ", " Madrid ", " Roma "};

        pregunta = new Pregunta(
                1,
                "Capital de Francia",
                opciones,
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
    public void testGetOpciones() { //devuelve las opciones
        assertEquals(3, pregunta.getOpciones().tamaño());
    }

    @Test
    public void testOpciones() {
        assertEquals("Paris", pregunta.getOpciones().obtener(0));
        assertEquals("Madrid", pregunta.getOpciones().obtener(1));
        assertEquals("Roma", pregunta.getOpciones().obtener(2));
    }

    @Test
    public void testEsCorrectaTrue() {
        assertTrue(pregunta.esCorrecta("Paris"));
    }

    @Test
    public void testEsCorrectaFalse() {
        assertFalse(pregunta.esCorrecta("Madrid"));
    }
}
