package com.edumind.negocio;

import com.edumind.datos.Materia;
import com.edumind.datos.Tarea;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GestorPrioridades {

    public int calcularPrioridad(Tarea tarea) {
        Materia m = tarea.getMateria();

        int importancia = m.getImportancia();      // 1 a 5
        int complejidad = tarea.getComplejidad();  // 1 a 5
        int tiempo = tarea.getTiempo();            // horas estimadas

        // 1. Base de prioridad
        int prioridad = importancia * 2 + complejidad;

        // 2. Ajuste por tiempo estimado
        if (tiempo >= 4) prioridad += 2;
        if (tiempo >= 8) prioridad += 1;

        // 3. Ajuste por fecha de entrega
        LocalDate hoy = LocalDate.now();
        LocalDate entrega = tarea.getFechaEntrega();

        long diasRestantes = ChronoUnit.DAYS.between(hoy, entrega);

        if (diasRestantes <= 1) {
            prioridad += 4;  // Muy urgente
        } else if (diasRestantes <= 3) {
            prioridad += 3;
        } else if (diasRestantes <= 7) {
            prioridad += 2;
        } else if (diasRestantes <= 14) {
            prioridad += 1;
        }

        // 4. Normalizar a rango 1..10
        if (prioridad < 1) prioridad = 1;
        if (prioridad > 10) prioridad = 10;

        return prioridad;
    }
}
