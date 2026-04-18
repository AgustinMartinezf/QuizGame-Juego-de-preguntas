package com;

import com.ucu.edu.aed.tda.TDAPila;

public class Jugador {

    private int idJugador;
    private String nombre;
    private int puntajeActual;
    private TDAPila<Respuesta> historial;

    public Jugador(int idJugador, String nombre) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.puntajeActual = 0;
        this.historial = new PilaEnlazada<>();
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
    public TDAPila<Respuesta> getHistorial() {
        return this.historial;
    }

    
}
