package com.example;

import com.Jugador;
import com.QuizGame;
import com.Respuesta;
import com.ucu.edu.aed.impl.ListaArrayList;
import com.ucu.edu.aed.tda.TDALista;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuizGameTest {

    private static TDALista<String> opciones(String... items) {
        TDALista<String> lista = new ListaArrayList<>(items.length);
        for (String s : items) lista.agregar(s);
        return lista;
    }

    private QuizGame nuevoJuegoConDosJugadoresYDosPreguntas() {
        QuizGame juego = new QuizGame();
        juego.registrarJugador(1, "Ana");
        juego.registrarJugador(2, "Bruno");
        juego.registrarPregunta(10, "Capital de Uruguay",
                opciones("Montevideo", "Buenos Aires"), "Montevideo", "Geo");
        juego.registrarPregunta(11, "2+2",
                opciones("3", "4"), "4", "Mat");
        return juego;
    }

    @Test
    public void responderCalculaPuntajesYDeterminaGanador() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();

        juego.responder("Montevideo");
        juego.responder("3");

        Jugador ganador = juego.determinarGanador();
        assertEquals("Ana", ganador.getNombre());
        assertEquals(juego.getPuntosPorCorrecta(), ganador.getPuntaje());
        assertTrue(juego.isPartidaTerminada());
    }

    @Test
    public void deshacerRevierteLaUltimaRespuestaYReinsertaLaPregunta() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();

        juego.responder("Montevideo");
        int puntajeAntes = juego.getGestorJugadores().buscarPorId(1).getPuntaje();
        assertEquals(juego.getPuntosPorCorrecta(), puntajeAntes);

        Respuesta revertida = juego.deshacer();
        assertNotNull(revertida);
        assertEquals(0, juego.getGestorJugadores().buscarPorId(1).getPuntaje());
        assertEquals("Ana", juego.jugadorActual().getNombre());
        assertEquals(10, juego.preguntaActual().getId());
        assertTrue(juego.hayPreguntasPendientes());
    }

    @Test
    public void deshacerLuegoDeTerminarReabrePartida() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();

        juego.responder("Montevideo");
        juego.responder("3");
        assertTrue(juego.isPartidaTerminada());

        juego.deshacer();
        assertEquals("Bruno", juego.jugadorActual().getNombre());
        assertEquals(11, juego.preguntaActual().getId());
        assertTrue(!juego.isPartidaTerminada());
    }

    @Test(expected = IllegalStateException.class)
    public void noSePuedeRegistrarJugadorDuranteLaPartida() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.registrarJugador(99, "Intruso");
    }

    @Test(expected = IllegalStateException.class)
    public void noSePuedeRegistrarPreguntaDuranteLaPartida() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.registrarPregunta(99, "X", opciones("a", "b"), "a", "C");
    }

    @Test(expected = IllegalStateException.class)
    public void noSePuedeEliminarPreguntaDuranteLaPartida() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.eliminarPregunta(10);
    }

    @Test(expected = IllegalStateException.class)
    public void noSePuedeReiniciarPartidaEnCurso() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.iniciarPartida();
    }

    @Test(expected = IllegalStateException.class)
    public void mostrarPuntajesAntesDeIniciarLanzaError() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.obtenerPuntajes();
    }

    @Test(expected = IllegalStateException.class)
    public void rankingAntesDeIniciarLanzaError() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.obtenerRanking();
    }

    @Test(expected = IllegalStateException.class)
    public void ganadorAntesDeIniciarLanzaError() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.determinarGanador();
    }

    @Test(expected = IllegalArgumentException.class)
    public void responderConOpcionInvalidaLanzaError() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.responder("respuesta inventada");
    }

    @Test
    public void responderConOpcionInvalidaNoConsumeTurno() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        Jugador antesJ = juego.jugadorActual();
        int antesPreg = juego.preguntaActual().getId();
        try {
            juego.responder("xxx");
        } catch (IllegalArgumentException ignored) { }
        assertEquals(antesJ.getNombre(), juego.jugadorActual().getNombre());
        assertEquals(antesPreg, juego.preguntaActual().getId());
    }

    @Test
    public void reiniciarPartidaTerminadaResetaPuntajesEHistorial() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.responder("Montevideo");
        juego.responder("4");
        assertEquals(juego.getPuntosPorCorrecta(),
                juego.getGestorJugadores().buscarPorId(1).getPuntaje());

        juego.iniciarPartida();
        assertEquals(0, juego.getGestorJugadores().buscarPorId(1).getPuntaje());
        assertEquals(0, juego.getGestorJugadores().buscarPorId(2).getPuntaje());
        assertTrue(juego.getGestorJugadores().buscarPorId(1).getHistorial().esVacio());
        assertTrue(juego.getGestorJugadores().buscarPorId(2).getHistorial().esVacio());
        assertTrue(juego.hayPreguntasPendientes());
    }

    @Test
    public void seReanudanGestionesUnaVezTerminadaLaPartida() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.responder("Montevideo");
        juego.responder("4");
        assertTrue(juego.isPartidaTerminada());

        juego.registrarJugador(3, "Carlos");
        juego.registrarPregunta(12, "1+1", opciones("1", "2"), "2", "Mat");
        assertEquals(3, juego.getGestorJugadores().cantidad());
        assertEquals(3, juego.getGestorPreguntas().cantidad());
    }
}
