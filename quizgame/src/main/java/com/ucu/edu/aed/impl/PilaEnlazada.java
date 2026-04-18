package com.ucu.edu.aed.impl;

import com.ucu.edu.aed.tda.TDAPila;
import com.ucu.edu.aed.tda.TDALista;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class PilaEnlazada<T> implements TDAPila<T> {

    private final ListaEnlazada<T> lista;

    public PilaEnlazada() {
        this.lista = new ListaEnlazada<>();
    }


    @Override
    public void mete(T dato) {
        lista.agregar(0, dato); 
    }

    @Override
    public T saca() {
        if (lista.esVacio()) {
            throw new NoSuchElementException("La pila está vacía.");
        }
        return lista.remover(0); 
    }

    @Override
    public T tope() {
        if (lista.esVacio()) {
            throw new NoSuchElementException("La pila está vacía.");
        }
        return lista.obtener(0); 
    }


    @Override
    public void agregar(T elem) {
        lista.agregar(0, elem);
    }

    @Override
    public void agregar(int index, T elem) {
        lista.agregar(index, elem);
    }

    @Override
    public T obtener(int index) {
        return lista.obtener(index);
    }

    @Override
    public T remover(int index) {
        return lista.remover(index);
    }

    @Override
    public boolean remover(T elem) {
        return lista.remover(elem);
    }

    @Override
    public boolean contiene(T elem) {
        return lista.contiene(elem);
    }

    @Override
    public int indiceDe(T elem) {
        return lista.indiceDe(elem);
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        return lista.buscar(criterio);
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        return lista.ordenar(comparator);
    }

    @Override
    public int tamaño() {
        return lista.tamaño();
    }

    @Override
    public boolean esVacio() {
        return lista.esVacio();
    }

    @Override
    public void vaciar() {
        lista.vaciar();
    }
}