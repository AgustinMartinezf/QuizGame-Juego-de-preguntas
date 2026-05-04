package com;

import com.ucu.edu.aed.impl.ListaArrayList;

public class Pregunta {
    private int idPregunta;
    private String enunciado;
    private ListaArrayList<String> opciones;
    private String respuestaCorrecta;
    private String categoria;
    private boolean esCorrecta;

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

    public boolean getEsCorrecta(){
        return this.esCorrecta;
    }

    public ListaArrayList<String> getOpciones(){
        return opciones;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public boolean esCorrecta(String pregunta){
        return this.respuestaCorrecta == pregunta;
    }

}
