package com.edumind.datos;

import java.time.LocalDate;
import java.time.LocalTime;

// Representa un bloque en la agenda: puede ser un horario de estudio o una tarea asignada
public class BloqueAgenda {

    private String descripcion;  // Nombre de la tarea o "Bloque libre"
    private String materia;      // Nombre de la materia (si aplica)
    private LocalDate fecha;     // Fecha del bloque
    private LocalTime inicio;    // Hora inicio
    private LocalTime fin;       // Hora fin
    private boolean esTarea;     // true si es bloque de tarea, false si es bloque de horario libre

    public BloqueAgenda(String descripcion, String s, LocalDate fechaActual, int i) {
        this.descripcion = descripcion;
        this.materia = materia;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.esTarea = esTarea;
    }

    // Getters
    public String getDescripcion() { return descripcion; }
    public String getMateria() { return materia; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getInicio() { return inicio; }
    public LocalTime getFin() { return fin; }
    public boolean isTarea() { return esTarea; }

    // Representación legible para la agenda
    @Override
    public String toString() {
        if (esTarea) {
            return String.format("[TAREA] %s (%s) %s - %s", descripcion, materia, inicio, fin);
        } else {
            return String.format("[HORARIO] %s - %s", inicio, fin);
        }
    }
}
