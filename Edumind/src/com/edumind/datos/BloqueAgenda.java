package com.edumind.datos;

import java.time.LocalDate;
import java.time.LocalTime;

public class BloqueAgenda {

    private LocalDate fecha;
    private LocalTime inicio;
    private LocalTime fin;

    private String descripcionTarea;
    private String materia;
    private int prioridad;
    private String tecnica;
    private String descripcionTecnica;
    private String pasos;
    private String cuandoUsar;

    private boolean esTarea;

    // CONSTRUCTOR PARA BLOQUE DE TAREA
    public BloqueAgenda(LocalDate fecha, LocalTime inicio, LocalTime fin,
                        String descripcionTarea, String materia,
                        int prioridad, String tecnica, String descripcionTecnica,
                        String pasos, String cuandoUsar) {
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.descripcionTarea = descripcionTarea;
        this.materia = materia;
        this.prioridad = prioridad;
        this.tecnica = tecnica;
        this.descripcionTecnica = descripcionTecnica;
        this.pasos = pasos;
        this.cuandoUsar = cuandoUsar;
        this.esTarea = true;
    }

    // CONSTRUCTOR PARA BLOQUE LIBRE
    public BloqueAgenda(LocalDate fecha, LocalTime inicio, LocalTime fin) {
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.esTarea = false;
    }

    public boolean isTarea() {
        return esTarea;
    }

    @Override
    public String toString() {
        if (!esTarea) {
            return String.format("[HORARIO LIBRE]  %s  %s - %s",
                    fecha, inicio, fin);
        }

        return String.format(
                "[TAREA]  %s  %s - %s\n" +
                        "Materia: %s  |  Prioridad: %d\n" +
                        "Técnica: %s\n" +
                        "Descripción técnica: %s\n" +
                        "Pasos: %s\n" +
                        "Cuándo usar: %s\n",
                fecha, inicio, fin,
                materia, prioridad,
                tecnica,
                descripcionTecnica,
                pasos,
                cuandoUsar
        );
    }
}
