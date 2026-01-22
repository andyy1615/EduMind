package com.edumind.estructuras;

import com.edumind.datos.Materia;

public class ArbolMaterias {

    private NodoMateria raiz;

    public void insertar(Materia materia) {
        raiz = insertarRec(raiz, materia);
    }

    private NodoMateria insertarRec(NodoMateria actual, Materia materia) {
        if (actual == null) {
            return new NodoMateria(materia);
        }

        if (materia.getNombre().compareToIgnoreCase(actual.materia.getNombre()) < 0) {
            actual.izquierda = insertarRec(actual.izquierda, materia);
        } else {
            actual.derecha = insertarRec(actual.derecha, materia);
        }

        return actual;
    }

    public boolean buscar(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private boolean buscarRec(NodoMateria actual, String nombre) {
        if (actual == null) {
            return false;
        }

        if (actual.materia.getNombre().equalsIgnoreCase(nombre)) {
            return true;
        }

        if (nombre.compareToIgnoreCase(actual.materia.getNombre()) < 0) {
            return buscarRec(actual.izquierda, nombre);
        } else {
            return buscarRec(actual.derecha, nombre);
        }
    }
}
