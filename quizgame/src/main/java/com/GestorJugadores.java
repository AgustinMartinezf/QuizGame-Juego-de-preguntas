package com;

import com.ucu.edu.aed.tda.TDALista;
import com.ucu.edu.aed.impl.ListaArrayList;

public class GestorJugadores {

    private final TDALista<Jugador>  jugadores = new ListaArrayList<>();
    
    /**
     * Registra un nuevo jugador. Lanza excepción si el ID ya existe.
     */
    public void registrarJugador(int idJugador, String nombre) {
        if (existeJugador(idJugador)) {
            throw new IllegalArgumentException(
                    "Ya existe un jugador con el ID: " + idJugador);
        }
        jugadores.agregar(new Jugador(idJugador, nombre));
    }
 
    /**
     * Busca un jugador por ID. Retorna Optional vacío si no existe.
     */
    public Jugador buscarPorId(int idJugador) {
        for (int i = 0; i < jugadores.tamaño(); i++) {
            Jugador j = jugadores.obtener(i);
            if (j.getId() == idJugador) {
                return j;
            }
        }
        return null;
    }
 
    /** Retorna true si existe un jugador con ese ID. */
    public boolean existeJugador(int idJugador) {
        return buscarJugador(idJugador) != null;
    }
 
  
}
}
