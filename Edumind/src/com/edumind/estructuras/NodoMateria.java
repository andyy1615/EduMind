package com.edumind.estructuras;

import com.edumind.datos.Materia;

public class NodoMateria {
    Materia materia;
    NodoMateria izquierda;
    NodoMateria derecha;

    public NodoMateria(Materia materia) {
        this.materia = materia;
    }
}
