package com;

import com.ucu.edu.aed.tda.TDALista;

public class Main {

    public static void main(String[] args) {
        QuizGame juego = new QuizGame();

        juego.registrarJugador(1, "Ana");
        juego.registrarJugador(2, "Bruno");

        juego.registrarPregunta(101, "Capital de Uruguay",
                new String[]{"Montevideo", "Buenos Aires", "Santiago"},
                "Montevideo", "Geografia");
        juego.registrarPregunta(102, "2 + 2",
                new String[]{"3", "4", "5"},
                "4", "Matematica");
        juego.registrarPregunta(103, "Color del cielo",
                new String[]{"Verde", "Azul", "Rojo"},
                "Azul", "Ciencia");
        juego.registrarPregunta(104, "Autor del Quijote",
                new String[]{"Cervantes", "Borges", "Cortazar"},
                "Cervantes", "Literatura");

        juego.iniciarPartida();

        String[] respuestas = {"Montevideo", "5", "Azul", "Borges"};
        int i = 0;
        while (juego.hayPreguntasPendientes()) {
            Jugador actual = juego.jugadorActual();
            Pregunta pregunta = juego.preguntaActual();
            System.out.println("Turno de " + actual.getNombre() + ": " + pregunta.getEnunciado());
            Respuesta r = juego.responder(respuestas[i]);
            if (r.isCorrecta()) {
                System.out.println("  respondio " + respuestas[i] + " (correcta, +" + r.getPuntosOtorgados() + ")");
            } else {
                System.out.println("  respondio " + respuestas[i] + " (incorrecta)");
            }
            i++;
        }

        System.out.println();
        System.out.println("Puntajes finales:");
        TDALista<Jugador> jugadores = juego.obtenerPuntajes();
        for (int k = 0; k < jugadores.tamaño(); k++) {
            Jugador j = jugadores.obtener(k);
            System.out.println("  " + j.getNombre() + ": " + j.getPuntaje());
        }

        Jugador ganador = juego.determinarGanador();
        System.out.println();
        System.out.println("Ganador: " + ganador.getNombre() + " con " + ganador.getPuntaje() + " puntos");

        System.out.println();
        System.out.println("Deshaciendo ultima respuesta de " + ganador.getNombre() + "...");
        Respuesta deshecha = juego.deshacer(ganador.getId());
        if (deshecha != null) {
            System.out.println("Se revirtio: " + deshecha);
            System.out.println("Nuevo puntaje de " + ganador.getNombre() + ": " + ganador.getPuntaje());
        }
    }
}
