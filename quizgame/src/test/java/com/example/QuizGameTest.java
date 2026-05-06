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
    public void deshacerRevierteLaUltimaRespuestaDelJugador() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();

        juego.responder("Montevideo");
        int puntajeAntes = juego.getGestorJugadores().buscarPorId(1).getPuntaje();
        assertEquals(juego.getPuntosPorCorrecta(), puntajeAntes);

        Respuesta revertida = juego.deshacer(1);
        assertNotNull(revertida);
        assertEquals(0, juego.getGestorJugadores().buscarPorId(1).getPuntaje());
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
        juego.registrarPregunta(99, "X", new String[]{"a", "b"}, "a", "C");
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

    @Test
    public void seReanudanGestionesUnaVezTerminadaLaPartida() {
        QuizGame juego = nuevoJuegoConDosJugadoresYDosPreguntas();
        juego.iniciarPartida();
        juego.responder("Montevideo");
        juego.responder("4");
        assertTrue(juego.isPartidaTerminada());

        juego.registrarJugador(3, "Carlos");
        juego.registrarPregunta(12, "1+1", new String[]{"1", "2"}, "2", "Mat");
        assertEquals(3, juego.getGestorJugadores().cantidad());
        assertEquals(3, juego.getGestorPreguntas().cantidad());
    }
}
