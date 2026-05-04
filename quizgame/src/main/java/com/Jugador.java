package com;

import com.ucu.edu.aed.impl.PilaEnlazada;
import com.ucu.edu.aed.tda.*;
import java.util.Objects;

public class Jugador {

    private final int idJugador;
    private final String nombre;
    private int puntajeActual;
    private final TDAPila<Respuesta> historial;

    public Jugador(int idJugador, String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.puntajeActual = 0;
        this.historial = new PilaEnlazada<>();
    }

    public int getId(){ 
        return idJugador; 
    }
    public String getNombre(){ 
        return nombre; 
    }
    public int getPuntaje(){ 
        return puntajeActual; 
    }
    public TDAPila<Respuesta> getHistorial(){ 
        return historial; 
    }

    public void sumarPuntos(int puntos) {
        this.puntajeActual += puntos;
    }

    public void restarPuntos(int puntos) {
        this.puntajeActual -= puntos;
    }

    public void reiniciarPuntaje() {
        this.puntajeActual = 0;
    }

    public void agregarRespuesta(Respuesta respuesta) {
        historial.mete(respuesta);
        sumarPuntos(respuesta.getPuntosOtorgados());
    }

    public Respuesta deshacerUltimaRespuesta() {
        if (historial.esVacio()) {
            return null;
        }
        Respuesta ultima = historial.saca();
        restarPuntos(ultima.getPuntosOtorgados());
        return ultima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador j)) return false;
        return idJugador == j.idJugador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJugador);
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + idJugador + ", Puntaje: " + puntajeActual + ")";
    }
}