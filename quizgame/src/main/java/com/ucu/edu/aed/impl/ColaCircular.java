package com.ucu.edu.aed.impl;

import com.ucu.edu.aed.tda.TDACola;
import com.ucu.edu.aed.tda.TDALista;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ColaCircular<T> implements TDACola<T> {

    private NodoCircular<T> frente;  
    private NodoCircular<T> fin;     
    private int tamaño;

    public ColaCircular() {
        this.frente = null;
        this.fin    = null;
        this.tamaño = 0;
    }


    private static class NodoCircular<T> {
        T dato;
        NodoCircular<T> siguiente;

        NodoCircular(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }


    @Override
    public boolean poneEnCola(T dato) {
        try {
            NodoCircular<T> nuevo = new NodoCircular<>(dato);
            if (frente == null) {

                frente = nuevo;
                fin = nuevo;
                nuevo.siguiente = frente;
            } else {
  
                nuevo.siguiente = frente;
                fin.siguiente = nuevo;
                fin = nuevo;
            }
            tamaño++;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public T frente() {
        if (frente == null) {
            throw new NoSuchElementException("La cola está vacía.");
        }
        return frente.dato;
    }

    @Override
    public T quitaDeCola() {
        if (frente == null) {
            throw new NoSuchElementException("La cola está vacía.");
        }
        T datoEliminado = frente.dato;

        if (frente == fin) {

            frente = null;
            fin = null;
        } else {
  
            frente = frente.siguiente;
            fin.siguiente = frente;
        }
        tamaño--;
        return datoEliminado;
    }

    @Override
    public void agregar(T elem) {
        poneEnCola(elem);
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        NodoCircular<T> nuevo = new NodoCircular<>(elem);

        if (tamaño == 0) {
            nuevo.siguiente = nuevo;
            frente = nuevo;
            fin = nuevo;

        } else if (index == 0) {
            nuevo.siguiente = frente;
            frente = nuevo;
            fin.siguiente   = frente; 

        } else if (index == tamaño) {
            nuevo.siguiente = frente;
            fin.siguiente   = nuevo;
            fin = nuevo;

        } else {
            NodoCircular<T> anterior = frente;
            for (int i = 0; i < index - 1; i++) {
                anterior = anterior.siguiente;
            }
            nuevo.siguiente    = anterior.siguiente;
            anterior.siguiente = nuevo;
        }
        tamaño++;
    }

    @Override
    public T obtener(int index) {
        checkIndex(index);
        NodoCircular<T> actual = frente;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    @Override
    public T remover(int index) {
        checkIndex(index);

        if (index == 0) {
            return quitaDeCola();
        }

        NodoCircular<T> anterior = frente;
        for (int i = 0; i < index - 1; i++) {
            anterior = anterior.siguiente;
        }
        NodoCircular<T> aEliminar = anterior.siguiente;
        anterior.siguiente = aEliminar.siguiente;

        if (aEliminar == fin) {
            fin = anterior;
        }
        tamaño--;
        return aEliminar.dato;
    }

    @Override
    public boolean remover(T elem) {
        if (frente == null) return false;

        NodoCircular<T> actual = frente;
        NodoCircular<T> anterior = fin; 

        for (int i = 0; i < tamaño; i++) {

            boolean coincide;

            if (elem == null) {
                coincide = (actual.dato == null);
            } else {
                coincide = elem.equals(actual.dato);
            }

            if (coincide) {
                if (tamaño == 1) {
                    frente = null;
                    fin = null;
                } else if (actual == frente) {
                    frente = frente.siguiente;
                    fin.siguiente = frente;
                } else {
                    anterior.siguiente = actual.siguiente;
                    if (actual == fin) {
                        fin = anterior;
                    }
                }
                tamaño--;
                return true;
            }

            anterior = actual;
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
        NodoCircular<T> actual = frente;

        for (int i = 0; i < tamaño; i++) {

            boolean coincide;

            if (elem == null) {
                coincide = (actual.dato == null);
            } else {
                coincide = elem.equals(actual.dato);
            }

            if (coincide) {
                return i;
            }

            actual = actual.siguiente;
        }

        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        NodoCircular<T> actual = frente;
        for (int i = 0; i < tamaño; i++) {
            try {
                if (criterio.test(actual.dato)) return actual.dato;
            } catch (Exception ignored) {}
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaEnlazada<T> aux = new ListaEnlazada<>();
        NodoCircular<T> actual = frente;
        for (int i = 0; i < tamaño; i++) {
            aux.agregar(actual.dato);
            actual = actual.siguiente;
        }
        return aux.ordenar(comparator);
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
        frente = null;
        fin = null;
        tamaño = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
}