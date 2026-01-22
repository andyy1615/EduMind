package com.edumind.datos;

import java.time.LocalTime;

public class HorarioEstudio {

    private String dia;
    private LocalTime inicio;
    private LocalTime fin;

    public HorarioEstudio(String dia, LocalTime inicio, LocalTime fin) {

        if (dia == null || dia.trim().isEmpty()) {
            throw new IllegalArgumentException("El día no puede estar vacío");
        }

        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las horas no pueden ser nulas");
        }

        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser menor que la hora de fin");
        }

        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public String getDia() {
        return dia;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    @Override
    public String toString() {
        return dia + ": " + inicio + " - " + fin;
    }
}
