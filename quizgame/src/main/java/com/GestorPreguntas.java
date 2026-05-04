package com;

import com.ucu.edu.aed.tda.TDALista;
import com.ucu.edu.aed.tda.TDACola;
import com.ucu.edu.aed.impl.ListaArrayList;
import com.ucu.edu.aed.impl.ColaEnlazada;

public class GestorPreguntas {

    private final TDALista<Pregunta> preguntas = new ListaArrayList<>();

    public void agregarPregunta(Pregunta pregunta) {
        if (buscarPorId(pregunta.getId()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una pregunta con el ID: " + pregunta.getId());
        }
        preguntas.agregar(pregunta);
    }

    public Pregunta buscarPorId(int idPregunta) {
        return preguntas.buscar(p -> p.getId() == idPregunta);
    }

    public void eliminarPregunta(int idPregunta) {
        for (int i = 0; i < preguntas.tamaño(); i++) {
            if (preguntas.obtener(i).getId() == idPregunta) {
                preguntas.remover(i);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No existe una pregunta con el ID: " + idPregunta);
    }


    public int cantidad() {
        return preguntas.tamaño();
    }


    public boolean existePregunta(int idPregunta) {
        return buscarPorId(idPregunta) != null;
    }


    public TDACola<Pregunta> obtenerColaPreguntas() {
        TDACola<Pregunta> cola = new ColaEnlazada<>();
        for (int i = 0; i < preguntas.tamaño(); i++) {
            cola.poneEnCola(preguntas.obtener(i));
        }
        return cola;
    }

    public TDALista<Pregunta> obtenerTodas() {
        return preguntas;
    }

}