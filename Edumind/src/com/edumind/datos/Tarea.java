package com.edumind.datos;

import java.time.LocalDate;

public class Tarea implements Comparable<Tarea> {

    private String descripcion;
    private LocalDate fechaEntrega;
    private int complejidad;
    private int tiempoMinutos;
    private int prioridad;
    private Materia materia;
    private String tipo;
    private boolean asignada = false;

    public Tarea(String descripcion, LocalDate fechaEntrega, int complejidad, int tiempoMinutos, Materia materia, String tipo) {
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.complejidad = complejidad;
        this.tiempoMinutos = tiempoMinutos;
        this.materia = materia;
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public int getComplejidad() {
        return complejidad;
    }

    public int getTiempo() {
        return tiempoMinutos;
    }

    public void setTiempo(int tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Materia getMateria() {
        return materia;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAsignada() {
        return asignada;
    }

    public void setAsignada(boolean asignada) {
        this.asignada = asignada;
    }

    @Override
    public int compareTo(Tarea otra) {
        return Integer.compare(otra.prioridad, this.prioridad);
    }
}
