package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.GestorJugadores;
import com.Jugador;

public class GestorJugadoresTest {
    private GestorJugadores gestor;
    private Jugador jugador1;
    private Jugador jugador2;

    @Before
    public void setUp() {
        gestor = new GestorJugadores();
        jugador1 = new Jugador(1, "Carlos");
        jugador2 = new Jugador(2, "Pedro");
    }

    @Test
    public void testRegistrarJugador() {
        gestor.registrarJugador(jugador1);

        assertEquals(1, gestor.cantidad());
        assertTrue(gestor.existeJugador(1));
        assertEquals(jugador1, gestor.buscarPorId(1));
    }

    @Test
    public void testRegistrarVariosJugadores() {
        gestor.registrarJugador(jugador1);
        gestor.registrarJugador(jugador2);

        assertEquals(2, gestor.cantidad());
        assertTrue(gestor.existeJugador(1));
        assertTrue(gestor.existeJugador(2));
    }

    @Test
    public void testRegistrarJugadorIdDuplicado() {
        gestor.registrarJugador(jugador1);

        try {
            gestor.registrarJugador(new Jugador(1, "Otro"));
            fail("Debía lanzar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Ya existe un jugador con el ID: 1", e.getMessage());
        }
    }

    @Test
    public void testBuscarPorIdExistente() {
        gestor.registrarJugador(jugador1);

        Jugador encontrado = gestor.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals(jugador1, encontrado);
    }

    @Test
    public void testBuscarPorIdInexistente() {
        Jugador encontrado = gestor.buscarPorId(99);

        assertNull(encontrado);
    }

    @Test
    public void testExisteJugadorTrue() {
        gestor.registrarJugador(jugador1);

        assertTrue(gestor.existeJugador(1));
    }

    @Test
    public void testExisteJugadorFalse() {
        assertFalse(gestor.existeJugador(1));
    }

    @Test
    public void testCantidadInicial() { //debería arracan en cero
        assertEquals(0, gestor.cantidad());
    }

    @Test
    public void testCantidadLuegoDeRegistrar() {
        gestor.registrarJugador(jugador1);
        gestor.registrarJugador(jugador2);

        assertEquals(2, gestor.cantidad());
    }

    @Test
    public void testObtenerTodos() {
        gestor.registrarJugador(jugador1);
        gestor.registrarJugador(jugador2);

        assertEquals(2, gestor.obtenerTodos().tamaño());
        assertEquals(jugador1, gestor.obtenerTodos().obtener(0));
        assertEquals(jugador2, gestor.obtenerTodos().obtener(1));
    }
}
