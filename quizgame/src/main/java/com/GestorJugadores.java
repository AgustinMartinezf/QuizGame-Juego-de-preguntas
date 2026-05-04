package com;

import com.ucu.edu.aed.tda.TDALista;
import com.ucu.edu.aed.impl.ListaArrayList;

public class GestorJugadores {

    private final TDALista<Jugador>  jugadores = new ListaArrayList<>();
    
    /**
     * Registra un nuevo jugador. Lanza excepción si el ID ya existe.
     */
    public void registrarJugador(Jugador jugador) {
        if (existeJugador(jugador.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe un jugador con el ID: " + jugador.getId());
        }
        jugadores.agregar(jugador);
    }
 
    /**
     * Busca un jugador por ID. Retorna Optional vacío si no existe.
     */
    public Jugador buscarPorId(int idJugador) {
       return jugadores.buscar(j->j.getId()==idJugador);
    }
 
    /** Retorna true si existe un jugador con ese ID. */
    public boolean existeJugador(int idJugador) {
        return buscarPorId(idJugador) != null;
    }

    public int cantidad(){
        return jugadores.tamaño();
    }
    public TDALista<Jugador> obtenerTodos(){
        return jugadores;
    }
 
}
