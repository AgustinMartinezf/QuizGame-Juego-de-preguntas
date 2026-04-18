package com.ucu.edu.aed.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileUtils {
    /**
     * Ejemplo de uso
     * FileUtils.leerLineas("./path/al/archivo/archivo.txt", linea -> {
     * System.out.println(linea);
     * });
     *
     * @param path     Ruta del archivo
     * @param consumer función que recibe cada linea del archivo
     */
    public static void leerLineas(String path, Consumer<String> consumer) {

        try {
            URL resource = FileUtils.class.getClassLoader().getResource(path);
            Path p = resource == null ? Paths.get(path) : Paths.get(resource.toURI());

            // Normalizamos cada línea antes de enviarla al consumidor:
            // - Si la línea comienza con el BOM UTF-8 (U+FEFF) lo removemos.
            //   Algunos editores/archivos incluidos en el JAR pueden contener
            //   este carácter invisible, que al imprimirse aparece como '?'
            //   y evita que comparaciones/remociones de cadenas coincidan.
            // - Se aplica `trim()` para eliminar espacios en los extremos.
            for (String line : Files.readAllLines(p)) {
                if (line != null && !line.isEmpty() && line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }
                if (line != null) {
                    line = line.trim();
                }
                consumer.accept(line);
            }
        } catch (IOException | URISyntaxException e) {

            System.err.println("Error al leer el archivo " + path);
            System.err.println(e.getLocalizedMessage());
        }

    }

    /**
     * Guarda contenido en el path indicado
     *
     * @param path      ruta de persistencia
     * @param contenido string del contenido
     */
    public static void escribirLineas(String path, String... contenido) {
        try {
            Files.write(Paths.get(path), Arrays.asList(contenido));
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo " + path);
            System.err.println(e.getLocalizedMessage());
        }
    }
}
