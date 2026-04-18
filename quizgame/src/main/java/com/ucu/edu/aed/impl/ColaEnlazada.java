package com.ucu.edu.aed.impl;

import com.ucu.edu.aed.tda.TDACola;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ColaEnlazada<T> implements TDACola<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo<T> anterior;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> cabeza;

    private Nodo<T> fin;

    private int tamaño;

    public ColaEnlazada() {
        cabeza = null;
        fin    = null;
        tamaño = 0;
    }

    @Override
    public T frente() {
        validarNoVacio();
        return cabeza.dato;
    }

    @Override
    public boolean poneEnCola(T dato) {
        agregar(dato);
        return true;
    }

    @Override
    public T quitaDeCola() {
        validarNoVacio();
        return remover(0);
    }

    @Override
    public void agregar(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (esVacio()) {
            cabeza = nuevo;
        } else {
            fin.siguiente  = nuevo;
            nuevo.anterior = fin;
        }
        fin = nuevo;
        tamaño++;
    }

    @Override
    public void agregar(int index, T elem) {
        validarIndiceInsercion(index);

        if (index == tamaño) {        
            agregar(elem);
            return;
        }

        Nodo<T> nuevo    = new Nodo<>(elem);
        Nodo<T> actual   = nodoPorIndice(index);

        nuevo.siguiente  = actual;
        nuevo.anterior   = actual.anterior;

        if (actual.anterior != null) {
            actual.anterior.siguiente = nuevo;
        } else {
            cabeza = nuevo;        
        }
        actual.anterior = nuevo;
        tamaño++;
    }


    @Override
    public T obtener(int index) {
        validarIndice(index);
        return nodoPorIndice(index).dato;
    }


    @Override
    public T remover(int index) {
        validarIndice(index);
        Nodo<T> objetivo = nodoPorIndice(index);
        return desenlazar(objetivo);
    }


    @Override
    public boolean remover(T elem) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (iguales(actual.dato, elem)) {
                desenlazar(actual);
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }


    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }


    @Override
    public int indiceDe(T elem) {
        Nodo<T> actual = cabeza;
        int     idx    = 0;
        while (actual != null) {
            if (iguales(actual.dato, elem)) return idx;
            actual = actual.siguiente;
            idx++;
        }
        return -1;
    }


    @Override
    public T buscar(Predicate<T> criterio) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (criterio.test(actual.dato)) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public TDACola<T> ordenar(Comparator<T> comparator) {
        Object[] arr = new Object[tamaño];
        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamaño; i++) {
            arr[i] = actual.dato;
            actual = actual.siguiente;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                @SuppressWarnings("unchecked")
                int cmp = comparator.compare((T) arr[j], (T) arr[minIdx]);
                if (cmp < 0) minIdx = j;
            }
            Object temp   = arr[minIdx];
            arr[minIdx]   = arr[i];
            arr[i]        = temp;
        }

        ColaEnlazada<T> ordenada = new ColaEnlazada<>();
        for (Object o : arr) {
            @SuppressWarnings("unchecked")
            T dato = (T) o;
            ordenada.agregar(dato);
        }
        return ordenada;
    }

    @Override
    public int tamaño() {
        return tamaño;
    }

    @Override
    public boolean esVacio() {
        return tamaño == 0;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        fin    = null;
        tamaño = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ColaEnlazada[frente -> ");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) sb.append(", ");
            actual = actual.siguiente;
        }
        sb.append(" <- fin]");
        return sb.toString();
    }


    private T desenlazar(Nodo<T> nodo) {
        if (nodo.anterior != null) {
            nodo.anterior.siguiente = nodo.siguiente;
        } else {
            cabeza = nodo.siguiente;   
        }

        if (nodo.siguiente != null) {
            nodo.siguiente.anterior = nodo.anterior;
        } else {
            fin = nodo.anterior;     
        }

        tamaño--;
        T dato = nodo.dato;
        nodo.dato      = null;         
        nodo.siguiente = null;
        nodo.anterior  = null;
        return dato;
    }


    private Nodo<T> nodoPorIndice(int index) {
        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        return actual;
    }

    private void validarIndice(int index) {
        if (index < 0 || index >= tamaño) {
            throw new IndexOutOfBoundsException(
                "Índice " + index + " fuera de rango [0, " + (tamaño - 1) + "]");
        }
    }

    private void validarIndiceInsercion(int index) {
        if (index < 0 || index > tamaño) {
            throw new IndexOutOfBoundsException(
                "Índice de inserción " + index + " fuera de rango [0, " + tamaño + "]");
        }
    }


    private void validarNoVacio() {
        if (esVacio()) {
            throw new NoSuchElementException("La cola está vacía.");
        }
    }

    private boolean iguales(T a, T b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }
}