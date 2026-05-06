package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.Pregunta;
import com.ucu.edu.aed.impl.ListaArrayList;
import com.ucu.edu.aed.tda.TDALista;

public class PreguntaTest {
    private Pregunta pregunta;

    private static TDALista<String> opciones(String... items) {
        TDALista<String> lista = new ListaArrayList<>(items.length);
        for (String s : items) lista.agregar(s);
        return lista;
    }

    @Before
    public void setUp() {
        pregunta = new Pregunta(
                1,
                "Capital de Francia",
                opciones("Paris", "Madrid", "Roma"),
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
        TDALista<String> ops = pregunta.getOpciones();
        assertEquals(3, ops.tamaño());
        assertEquals("Paris", ops.obtener(0));
        assertEquals("Madrid", ops.obtener(1));
        assertEquals("Roma", ops.obtener(2));
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
                () -> new Pregunta(2, "  ", opciones("a", "b"), "a", "Cat"));
    }

    @Test
    public void testMenosDeDosOpcionesLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta(2, "E", opciones("a"), "a", "Cat"));
    }
}
