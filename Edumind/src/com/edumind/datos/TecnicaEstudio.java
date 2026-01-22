package com.edumind.datos;

public class TecnicaEstudio {
    private String nombre;
    private String descripcion;
    private String pasos;
    private String cuandoUsar;

    public TecnicaEstudio(String nombre, String descripcion, String pasos, String cuandoUsar) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pasos = pasos;
        this.cuandoUsar = cuandoUsar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPasos() {
        return pasos;
    }

    public String getCuandoUsar() {
        return cuandoUsar;
    }

    @Override
    public String toString() {
        return nombre + "\n" + descripcion + "\n\nPasos:\n" + pasos + "\n\nCuándo usar:\n" + cuandoUsar;
    }
}
