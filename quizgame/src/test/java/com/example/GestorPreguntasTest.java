package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.GestorPreguntas;
import com.Pregunta;
import com.ucu.edu.aed.impl.ListaArrayList;
import com.ucu.edu.aed.tda.TDACola;
import com.ucu.edu.aed.tda.TDALista;

public class GestorPreguntasTest {

    private GestorPreguntas gestor;

    private Pregunta crearPregunta(int id) {
        TDALista<String> opciones = new ListaArrayList<>(3);
        opciones.agregar("A");
        opciones.agregar("B");
        opciones.agregar("C");
        return new Pregunta(id, "Pregunta " + id, opciones, "A", "General");
    }

    @Before
    public void setUp() {
        gestor = new GestorPreguntas();
    }

    @Test
    public void agregarYBuscar() {
        gestor.agregarPregunta(crearPregunta(1));
        assertNotNull(gestor.buscarPorId(1));
        assertEquals(1, gestor.cantidad());
    }

    @Test
    public void agregarDuplicadoLanzaExcepcion() {
        gestor.agregarPregunta(crearPregunta(1));
        assertThrows(IllegalArgumentException.class,
                () -> gestor.agregarPregunta(crearPregunta(1)));
    }

    @Test
    public void buscarInexistenteRetornaNull() {
        assertNull(gestor.buscarPorId(999));
    }

    @Test
    public void eliminarExistente() {
        gestor.agregarPregunta(crearPregunta(1));
        gestor.agregarPregunta(crearPregunta(2));
        gestor.eliminarPregunta(1);
        assertEquals(1, gestor.cantidad());
        assertNull(gestor.buscarPorId(1));
        assertNotNull(gestor.buscarPorId(2));
    }

    @Test
    public void eliminarInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> gestor.eliminarPregunta(999));
    }

    @Test
    public void obtenerColaPreguntasOrdenFIFO() {
        gestor.agregarPregunta(crearPregunta(1));
        gestor.agregarPregunta(crearPregunta(2));
        gestor.agregarPregunta(crearPregunta(3));

        TDACola<Pregunta> cola = gestor.obtenerColaPreguntas();
        assertEquals(3, cola.tamaño());
        assertEquals(1, cola.quitaDeCola().getId());
        assertEquals(2, cola.quitaDeCola().getId());
        assertEquals(3, cola.quitaDeCola().getId());
    }

    @Test
    public void existePregunta() {
        gestor.agregarPregunta(crearPregunta(5));
        assertTrue(gestor.existePregunta(5));
        assertFalse(gestor.existePregunta(6));
    }
}
