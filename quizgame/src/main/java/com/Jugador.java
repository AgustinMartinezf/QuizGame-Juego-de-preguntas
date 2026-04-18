package com;

public class Jugador {

    private int idJugador;
    private String nombre;
    private int puntajeActual;
    private Pila<Respuesta> historial;

    public Jugador(int idJugador, String nombre) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.puntajeActual = 0;
        this.historial = new Pila<>();
    }

    public void SumarPuntos(int puntos) {
        this.puntajeActual += puntos;
    }

    public int getPuntaje() {
        return this.puntajeActual;
    }

    public int getId() {
        return this.idJugador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void agregarRespuesta(Respuesta respuesta) {
        this.historial.push(respuesta);
    }
    public Pila<Respuesta> getHistorial() {
        return this.historial;
    }

    
}
