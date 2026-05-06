package com;
import com.ucu.edu.aed.tda.*;
import com.ucu.edu.aed.impl.*;

public class QuizGame {

    private static final int PUNTOS_POR_CORRECTA = 10;
    private final GestorJugadores gestorJugadores;
    private final GestorPreguntas gestorPreguntas;
    private final ColaCircular<Jugador> colaTurnos;
    private TDACola<Pregunta> colaPreguntasPendientes;
    private boolean partidaIniciada;
    private boolean partidaTerminada;

    public QuizGame() {
        this.gestorJugadores = new GestorJugadores();
        this.gestorPreguntas = new GestorPreguntas();
        this.colaTurnos = new ColaCircular<>();
        this.colaPreguntasPendientes = new ColaEnlazada<>();
        this.partidaIniciada = false;
        this.partidaTerminada = false;
    }

    private void validarNoEnCurso() {
        if (partidaIniciada && !partidaTerminada) {
            throw new IllegalStateException(
                    "No se puede modificar jugadores ni preguntas mientras la partida está en curso.");
        }
    }

    public void registrarJugador(int id, String nombre) {
        validarNoEnCurso();
        Jugador j = new Jugador(id, nombre);
        gestorJugadores.registrarJugador(j);
    }

    public void registrarPregunta(int id, String enunciado, TDALista<String> opciones, String respuestaCorrecta, String categoria) {
        validarNoEnCurso();
        Pregunta p = new Pregunta(id, enunciado, opciones, respuestaCorrecta, categoria);
        gestorPreguntas.agregarPregunta(p);
    }

    public void eliminarPregunta(int idPregunta) {
        validarNoEnCurso();
        gestorPreguntas.eliminarPregunta(idPregunta);
    }

    public void iniciarPartida() {
        validarNoEnCurso();
        if (gestorJugadores.cantidad() < 2) {
            throw new IllegalStateException("Se necesitan al menos 2 jugadores para iniciar.");
        }
        if(gestorPreguntas.cantidad()<gestorJugadores.cantidad()){
               throw new IllegalStateException("Se necesitan "+ ((gestorJugadores.cantidad())-(gestorPreguntas.cantidad())) + " preguntas más para comenzar el juego"); 
            }
        if (gestorPreguntas.cantidad() % gestorJugadores.cantidad() != 0) {
            int faltan = gestorJugadores.cantidad() - (gestorPreguntas.cantidad() % gestorJugadores.cantidad());
            throw new IllegalStateException("Debes agregar " + faltan + " preguntas más para comenzar el juego");
        }

        colaTurnos.vaciar();
        TDALista<Jugador> todos = gestorJugadores.obtenerTodos();
        for (int i = 0; i < todos.tamaño(); i++) {
            colaTurnos.poneEnCola(todos.obtener(i));
        }
        colaPreguntasPendientes = gestorPreguntas.obtenerColaPreguntas();
        partidaIniciada = true;
        partidaTerminada = false;
    }

    public Jugador jugadorActual() {
        if (!partidaIniciada || partidaTerminada) {
            throw new IllegalStateException("La partida no está en curso.");
        }
        return colaTurnos.frente();
    }

    public Pregunta preguntaActual() {
        if (colaPreguntasPendientes.esVacio()) {
            throw new IllegalStateException("No hay más preguntas pendientes.");
        }
        return colaPreguntasPendientes.frente();
    }

    public boolean hayPreguntasPendientes() {
        return !colaPreguntasPendientes.esVacio();
    }

    public Respuesta responder(String respuestaDada) {
        if (!partidaIniciada || partidaTerminada) {
            throw new IllegalStateException("La partida no está en curso.");
        }
        if (colaPreguntasPendientes.esVacio()) {
            throw new IllegalStateException("No hay más preguntas pendientes.");
        }

        Jugador jugador = colaTurnos.quitaDeCola();
        Pregunta pregunta = colaPreguntasPendientes.quitaDeCola();
        Respuesta respuesta = new Respuesta(pregunta, respuestaDada, PUNTOS_POR_CORRECTA);
        jugador.agregarRespuesta(respuesta);
        colaTurnos.poneEnCola(jugador);

        if (colaPreguntasPendientes.esVacio()) {
            partidaTerminada = true;
        }
        return respuesta;
    }

    public Respuesta deshacer() {
        if (!partidaIniciada) {
            throw new IllegalStateException("La partida no está iniciada.");
        }
        if (colaTurnos.esVacio()) {
            throw new IllegalStateException("No hay jugadores en la partida.");
        }
        int n = colaTurnos.tamaño();
        Jugador ultimoEnResponder = colaTurnos.obtener(n - 1);
        if (ultimoEnResponder.getHistorial().esVacio()) {
            throw new IllegalStateException("Todavía no hay respuestas para deshacer.");
        }

        Respuesta revertida = ultimoEnResponder.deshacerUltimaRespuesta();
        colaPreguntasPendientes.agregar(0, revertida.getPregunta());

        for (int i = 0; i < n - 1; i++) {
            colaTurnos.poneEnCola(colaTurnos.quitaDeCola());
        }

        partidaTerminada = false;
        return revertida;
    }

    private void validarPartidaYaIniciada() {
        if (!partidaIniciada) {
            throw new IllegalStateException(
                    "La partida no fue iniciada todavía: aún no hay puntajes para mostrar.");
        }
    }

    public TDALista<Jugador> obtenerPuntajes() {
        validarPartidaYaIniciada();
        return gestorJugadores.obtenerTodos();
    }

    public Jugador determinarGanador() {
        validarPartidaYaIniciada();
        TDALista<Jugador> jugadores = gestorJugadores.obtenerTodos();
        if (jugadores.esVacio()) {
            throw new IllegalStateException("No hay jugadores registrados.");
        }
        Jugador ganador = jugadores.obtener(0);
        for (int i = 1; i < jugadores.tamaño(); i++) {
            Jugador j = jugadores.obtener(i);
            if (j.getPuntaje() > ganador.getPuntaje()) {
                ganador = j;
            }
        }
        return ganador;
    }

    public TDALista<Jugador> obtenerRanking() {
        validarPartidaYaIniciada();
        TDALista<Jugador> jugadores = gestorJugadores.obtenerTodos();
        return jugadores.ordenar((a, b) -> Integer.compare(b.getPuntaje(), a.getPuntaje()));
    }

    public GestorJugadores getGestorJugadores(){
        return gestorJugadores; 
    }
    public GestorPreguntas getGestorPreguntas(){
        return gestorPreguntas; 
    }
    public boolean isPartidaIniciada(){ 
        return partidaIniciada; 
    }
    public boolean isPartidaTerminada(){ 
        return partidaTerminada; 
    }
    public int getPuntosPorCorrecta(){ 
        return PUNTOS_POR_CORRECTA; 
    }
}
