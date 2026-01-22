package com.edumind.negocio;

import com.edumind.datos.BloqueAgenda;
import com.edumind.datos.HorarioEstudio;
import com.edumind.datos.Tarea;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PlanificadorEstudio {

    public ArrayList<BloqueAgenda> generarAgenda(
            PriorityQueue<Tarea> tareas,
            ArrayList<HorarioEstudio> horarios,
            LocalDate inicio,
            LocalDate fin) {

        ArrayList<BloqueAgenda> agenda = new ArrayList<>();

        // Convertimos a lista ordenada por prioridad (la PriorityQueue ya lo hace)
        PriorityQueue<Tarea> copiaTareas = new PriorityQueue<>(tareas);

        // Recorrer cada día del rango
        LocalDate fechaActual = inicio;
        while (!fechaActual.isAfter(fin)) {

            // Para cada horario del día
            for (HorarioEstudio h : horarios) {

                LocalTime horaInicio = h.getInicio();
                LocalTime horaFin = h.getFin();

                // Mientras haya tiempo en el bloque
                while (horaInicio.isBefore(horaFin)) {

                    if (copiaTareas.isEmpty()) {
                        // No hay tareas: se marca como libre
                        agenda.add(new BloqueAgenda(
                                "Libre",               // titulo
                                "Sin tarea asignada",  // descripcion
                                fechaActual,           // fecha
                                1                      // duración (ejemplo 1 hora)
                        ));
                        break;
                    }

                    Tarea tarea = copiaTareas.peek();

                    // Cada unidad de tiempo se considera 1 hora
                    int tiempoTarea = tarea.getTiempo();

                    LocalTime finBloque = horaInicio.plusHours(tiempoTarea);

                    // Si no cabe en el horario, se sale del ciclo
                    if (finBloque.isAfter(horaFin)) {
                        agenda.add(new BloqueAgenda(
                                "Libre",
                                "No cabe la tarea en este horario",
                                fechaActual,
                                1
                        ));
                        break;
                    }

                    // Se agrega el bloque con la tarea
                    agenda.add(new BloqueAgenda(
                            tarea.getDescripcion(),   // titulo
                            "Materia: " + tarea.getMateria().getNombre() +
                                    " | Tipo: " + tarea.getTipo(),
                            fechaActual,
                            tiempoTarea
                    ));

                    // Se avanza en el horario
                    horaInicio = finBloque;

                    // Se elimina la tarea (ya asignada)
                    copiaTareas.poll();
                }
            }

            fechaActual = fechaActual.plusDays(1);
        }

        return agenda;
    }
}
