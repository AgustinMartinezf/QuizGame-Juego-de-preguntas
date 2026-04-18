package com.ucu.edu.aed.impl;

import com.ucu.edu.aed.tda.TDALista;

import java.util.Comparator;
import java.util.function.Predicate;


public class ListaArrayList<T> implements TDALista<T> {


    private static final int CAPACIDAD_INICIAL = 8;

    private Object[] datos;   
    private int tamanio;      

    public ListaArrayList() {
        datos   = new Object[CAPACIDAD_INICIAL];
        tamanio = 0;
    }


    private void validarIndice(int index) {
        if (index < 0 || index >= tamanio) {
            throw new IndexOutOfBoundsException(
                "Índice " + index + " fuera de rango [0, " + (tamanio - 1) + "].");
        }
    }

    private void validarIndiceInsercion(int index) {
        if (index < 0 || index > tamanio) {
            throw new IndexOutOfBoundsException(
                "Índice de inserción " + index + " fuera de rango [0, " + tamanio + "].");
        }
    }

    
    private void expandir() {
        Object[] nuevo = new Object[datos.length * 2];
        copiarArreglo(datos, nuevo, tamanio);
        datos = nuevo;
    }

    
    private void contraerSiCorresponde() {
        if (datos.length > CAPACIDAD_INICIAL && tamanio < datos.length / 4) {
            int nuevaCapacidad = Math.max(CAPACIDAD_INICIAL, datos.length / 2);
            Object[] nuevo = new Object[nuevaCapacidad];
            copiarArreglo(datos, nuevo, tamanio);
            datos = nuevo;
        }
    }

    private void copiarArreglo(Object[] origen, Object[] destino, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            destino[i] = origen[i];
        }
    }

    @Override
    public void agregar(T elem) {
        if (tamanio == datos.length) {
            expandir();
        }
        datos[tamanio] = elem;
        tamanio++;
    }


    @Override
    public void agregar(int index, T elem) {
        validarIndiceInsercion(index);
        if (tamanio == datos.length) {
            expandir();
        }
        // Desplazar elementos hacia la derecha desde el final hasta index
        for (int i = tamanio; i > index; i--) {
            datos[i] = datos[i - 1];
        }
        datos[index] = elem;
        tamanio++;
    }


    @Override
    public T obtener(int index) {
        validarIndice(index);
        return (T) datos[index];
    }


    @Override
    public T remover(int index) {
        validarIndice(index);
        T eliminado = (T) datos[index];
        // Desplazar elementos hacia la izquierda
        for (int i = index; i < tamanio - 1; i++) {
            datos[i] = datos[i + 1];
        }
        datos[tamanio - 1] = null;  // evitar retención de referencia (memory leak)
        tamanio--;
        contraerSiCorresponde();
        return eliminado;
    }


    @Override
    public boolean remover(T elem) {
        int idx = indiceDe(elem);
        if (idx == -1) return false;
        remover(idx);
        return true;
    }

    
    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

  
    @Override
    public int indiceDe(T elem) {
        for (int i = 0; i < tamanio; i++) {
            if (((T) datos[i]).equals(elem)) {
                return i;
            }
        }
        return -1;
    }

 
    @Override
    public T buscar(Predicate<T> criterio) {
        for (int i = 0; i < tamanio; i++) {
            T elem = (T) datos[i];
            if (criterio.test(elem)) {
                return elem;
            }
        }
        return null;
    }

   
    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        // Copiar elementos al nuevo arreglo temporal
        Object[] copia = new Object[tamanio];
        copiarArreglo(datos, copia, tamanio);

        // Insertion sort sobre la copia
        for (int i = 1; i < tamanio; i++) {
            T clave = (T) copia[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare((T) copia[j], clave) > 0) {
                copia[j + 1] = copia[j];
                j--;
            }
            copia[j + 1] = clave;
        }

        // Volcar al resultado
        ListaArrayList<T> resultado = new ListaArrayList<>();
        for (int i = 0; i < tamanio; i++) {
            resultado.agregar((T) copia[i]);
        }
        return resultado;
    }


    @Override
    public int tamaño() {
        return tamanio;
    }

    @Override
    public boolean esVacio() {
        return tamanio == 0;
    }

    @Override
    public void vaciar() {
        for (int i = 0; i < tamanio; i++) {
            datos[i] = null;
        }
        datos   = new Object[CAPACIDAD_INICIAL];
        tamanio = 0;
    }


    @Override
    public String toString() {
        if (tamanio == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanio; i++) {
            sb.append(datos[i]);
            if (i < tamanio - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
