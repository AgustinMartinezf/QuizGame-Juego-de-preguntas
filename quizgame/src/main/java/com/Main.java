package com;

import com.ucu.edu.aed.tda.TDALista;

import java.util.Scanner;

public class Main {

    private static final QuizGame juego = new QuizGame();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        System.out.println("QuizGame - Juego de Preguntas");
        

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1": registrarJugador(); break;
                case "2": registrarPregunta(); break;
                case "3": eliminarPregunta(); break;
                case "4": cargarDesdeCSV(); break;
                case "5": iniciarPartida(); break;
                case "6": jugarPartida(); break;
                case "7": deshacerRespuesta(); break;
                case "8": mostrarPuntajes(); break;
                case "9": mostrarGanador(); break;
                case "0": salir = true; break;
                default: System.out.println("Opcion no valida.");
            }
            System.out.println();
        }
        System.out.println("Hasta luego!");
    }

    private static void mostrarMenu() {
        System.out.println("----------------------------------");
        System.out.println("1. Registrar jugador");
        System.out.println("2. Registrar pregunta");
        System.out.println("3. Eliminar pregunta");
        System.out.println("4. Cargar datos desde CSV");
        System.out.println("5. Iniciar partida");
        System.out.println("6. Jugar partida (responder preguntas)");
        System.out.println("7. Deshacer ultima respuesta de un jugador");
        System.out.println("8. Mostrar puntajes");
        System.out.println("9. Mostrar ganador");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
    }

    private static void registrarJugador() {
        try {
            System.out.print("ID del jugador: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            juego.registrarJugador(id, nombre);
            System.out.println("Jugador registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarPregunta() {
        try {
            System.out.print("ID de la pregunta: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enunciado: ");
            String enunciado = scanner.nextLine().trim();
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();
            System.out.print("Cantidad de opciones: ");
            int n = Integer.parseInt(scanner.nextLine().trim());
            String[] opciones = new String[n];
            for (int i = 0; i < n; i++) {
                System.out.print("  Opcion " + (char) ('A' + i) + ": ");
                opciones[i] = scanner.nextLine().trim();
            }
            System.out.print("Respuesta correcta: ");
            String correcta = scanner.nextLine().trim();
            juego.registrarPregunta(id, enunciado, opciones, correcta, categoria);
            System.out.println("Pregunta registrada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarPregunta() {
        try {
            System.out.print("ID de la pregunta a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            juego.eliminarPregunta(id);
            System.out.println("Pregunta eliminada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cargarDesdeCSV() {
        System.out.print("Ruta del archivo de jugadores (o Enter para omitir): ");
        String rutaJugadores = scanner.nextLine().trim();
        if (!rutaJugadores.isEmpty()) {
            int c = CargadorCSV.cargarJugadores(juego, rutaJugadores);
            System.out.println("Jugadores cargados: " + c);
        }
        System.out.print("Ruta del archivo de preguntas (o Enter para omitir): ");
        String rutaPreguntas = scanner.nextLine().trim();
        if (!rutaPreguntas.isEmpty()) {
            int c = CargadorCSV.cargarPreguntas(juego, rutaPreguntas);
            System.out.println("Preguntas cargadas: " + c);
        }
    }

    private static void iniciarPartida() {
        try {
            juego.iniciarPartida();
            System.out.println("Partida iniciada!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void jugarPartida() {
        if (!juego.isPartidaIniciada() || juego.isPartidaTerminada()) {
            System.out.println("No hay partida en curso. Inicie una primero (opcion 5).");
            return;
        }

        while (juego.hayPreguntasPendientes()) {
            Jugador jugadorActual = juego.jugadorActual();
            Pregunta preguntaActual = juego.preguntaActual();

            System.out.println();
            System.out.println("-- Turno de: " + jugadorActual.getNombre() + " --");
            System.out.print(preguntaActual);
            System.out.print("Tu respuesta (o 'salir' para pausar): ");
            String resp = scanner.nextLine().trim();

            if (resp.equalsIgnoreCase("salir")) {
                System.out.println("Partida pausada.");
                return;
            }

            Respuesta resultado = juego.responder(resp);
            if (resultado.isCorrecta()) {
                System.out.println("Correcto! +" + resultado.getPuntosOtorgados() + " puntos.");
            } else {
                System.out.println("Incorrecto. La respuesta era: "
                        + preguntaActual.getRespuestaCorrecta());
            }
        }

        System.out.println();
        System.out.println("   La partida ha terminado!");
        mostrarPuntajes();
        mostrarGanador();
    }

    private static void deshacerRespuesta() {
        try {
            System.out.print("ID del jugador: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Respuesta deshecha = juego.deshacer(id);
            if (deshecha == null) {
                System.out.println("El jugador no tiene respuestas para deshacer.");
            } else {
                System.out.println("Respuesta deshecha: pregunta #" + deshecha.getPregunta().getId()
                        + " (se revertieron " + deshecha.getPuntosOtorgados() + " puntos).");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarPuntajes() {
        System.out.println();
        System.out.println("-- Puntajes --");
        TDALista<Jugador> ranking = juego.obtenerRanking();
        for (int i = 0; i < ranking.tamaño(); i++) {
            Jugador j = ranking.obtener(i);
            System.out.println("  " + (i + 1) + ". " + j);
        }
    }

    private static void mostrarGanador() {
        try {
            Jugador ganador = juego.determinarGanador();
            System.out.println();
            System.out.println("Ganador: " + ganador + "!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
