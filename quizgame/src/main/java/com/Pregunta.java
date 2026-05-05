package com;

import java.util.Objects;

public class Pregunta {

    private final int idPregunta;
    private final String enunciado;
    private final String[] opciones;
    private final String respuestaCorrecta;
    private final String categoria;

    public Pregunta(int idPregunta, String enunciado, String[] opciones,
                    String respuestaCorrecta, String categoria) {
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("El enunciado no puede ser vacío.");
        }
        if (opciones == null || opciones.length < 2) {
            throw new IllegalArgumentException("Debe haber al menos 2 opciones.");
        }
        if (respuestaCorrecta == null || respuestaCorrecta.isBlank()) {
            throw new IllegalArgumentException("La respuesta correcta no puede ser vacía.");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("La categoría no puede ser vacía.");
        }
        this.idPregunta = idPregunta;
        this.enunciado = enunciado;
        this.opciones = opciones.clone();
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
    }

    public int getId() {
        return idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getOpciones() {
        return opciones.clone();
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean esCorrecta(String respuesta) {
        if (respuesta == null) return false;
        return respuestaCorrecta.equalsIgnoreCase(respuesta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pregunta p)) return false;
        return idPregunta == p.idPregunta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPregunta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(categoria).append("] ").append(enunciado).append("\n");
        for (int i = 0; i < opciones.length; i++) {
            sb.append("  ").append((char) ('A' + i)).append(") ").append(opciones[i]).append("\n");
        }
        return sb.toString();
    }
}
