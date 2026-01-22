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

    private String tecnicaRecomendada;
    private String descripcionTecnica;
    private String pasosTecnica;
    private String cuandoUsar;

    public Tarea(String descripcion, LocalDate fechaEntrega, int complejidad, int tiempo, Materia materia, String tipo) {
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.complejidad = complejidad;
        this.tiempoMinutos = tiempo;
        this.materia = materia;
        this.tipo = tipo;
        this.tecnicaRecomendada = "";
        this.descripcionTecnica = "";
        this.pasosTecnica = "";
        this.cuandoUsar = "";
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

    public void setTiempo(int tiempo) {
        this.tiempoMinutos = tiempo;
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

    public String getTecnicaRecomendada() {
        return tecnicaRecomendada;
    }

    public void setTecnicaRecomendada(String tecnicaRecomendada) {
        this.tecnicaRecomendada = tecnicaRecomendada;
    }

    public String getDescripcionTecnica() {
        return descripcionTecnica;
    }

    public void setDescripcionTecnica(String descripcionTecnica) {
        this.descripcionTecnica = descripcionTecnica;
    }

    public String getPasosTecnica() {
        return pasosTecnica;
    }

    public void setPasosTecnica(String pasosTecnica) {
        this.pasosTecnica = pasosTecnica;
    }

    public String getCuandoUsar() {
        return cuandoUsar;
    }

    public void setCuandoUsar(String cuandoUsar) {
        this.cuandoUsar = cuandoUsar;
    }

    @Override
    public int compareTo(Tarea otra) {
        return Integer.compare(otra.prioridad, this.prioridad);
    }
}
