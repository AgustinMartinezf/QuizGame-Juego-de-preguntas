package com;


public class Respuesta {

    private final Pregunta pregunta;
    private final String respuestaDada;
    private final boolean correcta;
    private final int puntosOtorgados;

    public Respuesta(Pregunta pregunta, String respuestaDada, int puntosPorCorrecta) {
        this.pregunta = pregunta;
        this.respuestaDada = respuestaDada;
        this.correcta = pregunta.esCorrecta(respuestaDada);
        if (this.correcta) {
            this.puntosOtorgados = puntosPorCorrecta;
        } else {
            this.puntosOtorgados = 0;
        }
    }

    public Pregunta getPregunta() { return pregunta; }
    public String getRespuestaDada() { return respuestaDada; }
    public boolean isCorrecta() { return correcta; }
    public int getPuntosOtorgados() { return puntosOtorgados; }

    @Override
    public String toString() {
        return "Respuesta{pregunta=" + pregunta.getId() + ", dada='" + respuestaDada + "'" + ", correcta=" + correcta + ", puntos=" + puntosOtorgados + "}";
    }
}
