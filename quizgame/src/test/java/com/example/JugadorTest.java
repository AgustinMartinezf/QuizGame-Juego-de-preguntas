package com.example;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import com.*;

import org.junit.Before;
import org.junit.Test;

public class JugadorTest {

    private Jugador jugador;
    private Pregunta pregunta;

    @Before
    public void setUp() {
        jugador = new Jugador(1, "Carlos");

        String[] opciones = {"Paris", "Madrid", "Roma"};

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
        assertEquals(1, jugador.getId());
        assertEquals("Carlos", jugador.getNombre());
        assertEquals(0, jugador.getPuntaje());
        assertTrue(jugador.getHistorial().esVacio());
    }

    @Test
    public void testConstructorNombreVacio() {
        try {
            new Jugador(2, "");
            fail("Debía lanzar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("El nombre no puede ser vacío.", e.getMessage());
        }
    }

    @Test
    public void testConstructorNombreNull() {
        try {
            new Jugador(2, null);
            fail("Debía lanzar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("El nombre no puede ser vacío.", e.getMessage());
        }
    }

    @Test
    public void testSumarPuntos() {
        jugador.sumarPuntos(10);
        assertEquals(10, jugador.getPuntaje());
    }

    @Test
    public void testRestarPuntos() {
        jugador.sumarPuntos(10);
        jugador.restarPuntos(4);
        assertEquals(6, jugador.getPuntaje());
    }

    @Test
    public void testReiniciarPuntaje() {
        jugador.sumarPuntos(20);
        jugador.reiniciarPuntaje();
        assertEquals(0, jugador.getPuntaje());
    }

    @Test
    public void testAgregarRespuestaCorrecta() {
        Respuesta respuesta = new Respuesta(pregunta, "Paris", 5);

        jugador.agregarRespuesta(respuesta);

        assertEquals(5, jugador.getPuntaje());
        assertFalse(jugador.getHistorial().esVacio());
    }

    @Test
    public void testAgregarRespuestaIncorrecta() {
        Respuesta respuesta = new Respuesta(pregunta, "Madrid", 5);

        jugador.agregarRespuesta(respuesta);

        assertEquals(0, jugador.getPuntaje());
        assertFalse(jugador.getHistorial().esVacio());
    }

    @Test
    public void testDeshacerUltimaRespuesta() {
        Respuesta respuesta = new Respuesta(pregunta, "Paris", 8);

        jugador.agregarRespuesta(respuesta);

        Respuesta ultima = jugador.deshacerUltimaRespuesta();

        assertEquals(respuesta, ultima);
        assertEquals(0, jugador.getPuntaje());
        assertTrue(jugador.getHistorial().esVacio());
    }
}
