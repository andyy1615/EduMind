package com.edumind.negocio;

import com.edumind.datos.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PlanificadorEstudio {

    public ArrayList<BloqueAgenda> generarAgenda(PriorityQueue<Tarea> cola, ArrayList<HorarioEstudio> horarios,
                                                 LocalDate inicio, LocalDate fin, GestorAcademico gestor) {

        ArrayList<BloqueAgenda> agenda = new ArrayList<>();

        LocalDate fechaActual = inicio;

        while (!fechaActual.isAfter(fin)) {

            for (HorarioEstudio h : horarios) {

                LocalTime inicioBloque = h.getInicio();
                LocalTime finBloque = h.getFin();

                if (!cola.isEmpty()) {

                    Tarea tarea = cola.poll();

                    // Si no tiene técnica, asigna una real desde gestor
                    if (tarea.getTecnicaRecomendada() == null || tarea.getTecnicaRecomendada().isEmpty()) {
                        gestor.recomendarTecnica(tarea);
                    }

                    BloqueAgenda bloque = new BloqueAgenda(
                            fechaActual,
                            inicioBloque,
                            finBloque,
                            tarea.getDescripcion(),
                            tarea.getMateria().getNombre(),
                            tarea.getPrioridad(),
                            tarea.getTecnicaRecomendada(),
                            tarea.getDescripcionTecnica(),
                            tarea.getPasosTecnica(),
                            tarea.getCuandoUsar()
                    );

                    agenda.add(bloque);
                }
            }

            fechaActual = fechaActual.plusDays(1);
        }

        return agenda;
    }
}
