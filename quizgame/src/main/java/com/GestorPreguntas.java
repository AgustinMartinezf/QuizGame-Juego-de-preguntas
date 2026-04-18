package com;

import com.ucu.edu.aed.tda.TDALista;
import com.ucu.edu.aed.tda.TDACola;
import com.ucu.edu.aed.impl.ListaArrayList;
import com.ucu.edu.aed.impl.ColaEnlazada;

public class GestorPreguntas {

    private final TDALista<Pregunta> preguntas = new ListaArrayList<>();

    /**
     * Agrega una nueva pregunta. Lanza excepción si el ID ya existe.
     */
    public void agregarPregunta(int idPregunta, String enunciado, String[] opciones, String respuestaCorrecta, String categoria) {
        if (buscarPorId(idPregunta) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una pregunta con el ID: " + idPregunta);
        }
        preguntas.agregar(new Pregunta(idPregunta, enunciado, opciones, respuestaCorrecta, categoria));
    }

    /**
     * Busca una pregunta por ID. Retorna null si no existe.
     */
    public Pregunta buscarPorId(int idPregunta) {
        for (int i = 0; i < preguntas.tamaño(); i++) {
            Pregunta p = preguntas.obtener(i);
            if (p.getId() == idPregunta) {
                return p;
            }
        }
        return null;
    }

    /**
     * Elimina una pregunta por ID. Lanza excepción si no existe.
     */
    public void eliminarPregunta(int idPregunta) {
        for (int i = 0; i < preguntas.tamaño(); i++) {
            if (preguntas.obtener(i).getId() == idPregunta) {
                preguntas.eliminar(i);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No existe una pregunta con el ID: " + idPregunta);
    }

    /**
     * Retorna el arreglo de todas las preguntas.
     */
    public Pregunta[] obtenerPreguntas() {
        Pregunta[] resultado = new Pregunta[preguntas.tamaño()];
        for (int i = 0; i < preguntas.tamaño(); i++) {
            resultado[i] = preguntas.obtener(i);
        }
        return resultado;
    }

    /**
     * Retorna una cola con todas las preguntas en orden de inserción.
     */
    public TDACola<Pregunta> obtenerColaPreguntas() {
        TDACola<Pregunta> cola = new ColaEnlazada<>();
        for (int i = 0; i < preguntas.tamaño(); i++) {
            cola.encolar(preguntas.obtener(i));
        }
        return cola;
    }

}