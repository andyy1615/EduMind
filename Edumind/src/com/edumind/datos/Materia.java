package com.edumind.datos;

import java.util.ArrayList;

public class Materia {

    private String nombre;
    private int importancia;
    private ArrayList<Tarea> tareas;

    public Materia(String nombre, int importancia) {
        this.nombre = nombre;
        this.importancia = importancia;
        this.tareas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getImportancia() {
        return importancia;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    @Override
    public String toString() {
        return nombre + " (Importancia: " + importancia + ")";
    }
}
