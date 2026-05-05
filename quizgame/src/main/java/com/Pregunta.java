package com;

import com.ucu.edu.aed.impl.ListaArrayList;
import com.ucu.edu.aed.tda.TDALista;

public class Pregunta {
    private int idPregunta;
    private String enunciado;
    private TDALista<String> opciones;
    private String respuestaCorrecta;
    private String categoria;

    public Pregunta(int idPregunta, String enunciado, String[] opciones, String respuestaCorrecta,
        String categoria) {

        this.idPregunta = idPregunta;
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
        this.opciones = new ListaArrayList<>();

        for (String opcion : opciones) {
            this.opciones.agregar(opcion.trim());
        }
    }

    public int getId() {
        return this.idPregunta;
    }

    public String getEnunciado(){
        return this.enunciado;
    }

    public String getRespuestaCorrecta(){
        return this.respuestaCorrecta;
    }

    public TDALista<String> getOpciones(){
        return opciones;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public boolean esCorrecta(String respuesta){
        if (respuesta == null) return false;
        return this.respuestaCorrecta.equals(respuesta);
    }

}
