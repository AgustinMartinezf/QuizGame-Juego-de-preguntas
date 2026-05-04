package com;

import com.ucu.edu.aed.utils.FileUtils;

public class CargadorCSV {

    /**
     * Carga preguntas desde un archivo CSV al QuizGame.
     */
    public static int cargarPreguntas(QuizGame juego, String rutaArchivo) {
        int[] count = {0};
        FileUtils.leerLineas(rutaArchivo, linea -> {
            if (linea == null || linea.isBlank() || linea.startsWith("#")) return;
            String[] partes = linea.split(";");
            if (partes.length < 5) {
                System.err.println("Línea inválida (preguntas): " + linea);
                return;
            }
            try {
                int id = Integer.parseInt(partes[0].trim());
                String enunciado = partes[1].trim();
                String[] opciones = partes[2].trim().split(",");
                for (int i = 0; i < opciones.length; i++) {
                    opciones[i] = opciones[i].trim();
                }
                String correcta = partes[3].trim();
                String categoria = partes[4].trim();
                juego.registrarPregunta(id, enunciado, opciones, correcta, categoria);
                count[0]++;
            } catch (Exception e) {
                System.err.println("Error al procesar línea: " + linea + ": " + e.getMessage());
            }
        });
        return count[0];
    }

    /**
     * Carga jugadores desde un archivo CSV al QuizGame.
     */
    public static int cargarJugadores(QuizGame juego, String rutaArchivo) {
        int[] count = {0};
        FileUtils.leerLineas(rutaArchivo, linea -> {
            if (linea == null || linea.isBlank() || linea.startsWith("#")) return;
            String[] partes = linea.split(";");
            if (partes.length < 2) {
                System.err.println("Línea inválida (jugadores): " + linea);
                return;
            }
            try {
                int id = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                juego.registrarJugador(id, nombre);
                count[0]++;
            } catch (Exception e) {
                System.err.println("Error al procesar línea: " + linea + ": " + e.getMessage());
            }
        });
        return count[0];
    }
}
