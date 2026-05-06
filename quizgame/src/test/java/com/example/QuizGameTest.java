package com.example;

import com.Jugador;
import com.QuizGame;
import com.Respuesta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuizGameTest {

    private QuizGame nuevoJuegoConDosJugadoresYDosPreguntas() {
        QuizGame juego = new QuizGame();
        juego.registrarJugador(1, "Ana");
        juego.registrarJugador(2, "Bruno");
        juego.registrarPregunta(10, "Capital de Uruguay",
                new String[]{"Montevideo", "Buenos Aires"}, "Montevideo", "Geo");
        juego.registrarPregunta(11, "2+2",
                new String[]{"3", "4"}, "4", "Mat");
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
}
