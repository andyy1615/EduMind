package com.edumind.negocio;

import com.edumind.datos.*;
import com.edumind.estructuras.ArbolMaterias;
import com.edumind.estructuras.GrafoMaterias;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class GestorAcademico {

    private ArrayList<Materia> materias;
    private ArrayList<Tarea> tareas;
    private ArrayList<HorarioEstudio> horarios;
    private ArrayList<String> historial;

    private ArbolMaterias arbolMaterias;
    private GrafoMaterias grafoMaterias;

    public GestorAcademico() {
        materias = new ArrayList<>();
        tareas = new ArrayList<>();
        horarios = new ArrayList<>();
        historial = new ArrayList<>();
        arbolMaterias = new ArbolMaterias();
        grafoMaterias = new GrafoMaterias();
    }

    // =========================
    // MATERIAS
    // =========================
    public boolean agregarMateria(String nombre, int importancia) {
        if (buscarMateria(nombre) != null) {
            return false;
        }

        Materia m = new Materia(nombre, importancia);
        materias.add(m);
        arbolMaterias.insertar(m);
        grafoMaterias.agregarMateria(nombre);

        historial.add("Materia agregada: " + nombre);
        return true;
    }

    public Materia buscarMateria(String nombre) {
        for (Materia m : materias) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<String> getListaMaterias() {
        ArrayList<String> lista = new ArrayList<>();
        for (Materia m : materias) {
            lista.add(m.getNombre());
        }
        return lista;
    }

    public ArrayList<Materia> getListaMateriasObj() {
        return materias;
    }

    // =========================
    // TAREAS
    // =========================
    public boolean agregarTarea(String desc, LocalDate fechaEntrega, int complejidad,
                                int tiempo, String materiaNombre, String tipo) {

        Materia materia = buscarMateria(materiaNombre);
        if (materia == null) return false;

        Tarea t = new Tarea(desc, fechaEntrega, complejidad, tiempo, materia, tipo);
        tareas.add(t);
        materia.agregarTarea(t);

        historial.add("Tarea agregada: " + desc + " | Materia: " + materiaNombre);
        return true;
    }

    public ArrayList<Tarea> getListaTareas() {
        return tareas;
    }

    public Tarea getTareaPorIndice(int indice) {
        if (indice < 0 || indice >= tareas.size()) {
            return null;
        }
        return tareas.get(indice);
    }

    // =========================
    // HORARIOS
    // =========================
    public boolean agregarHorario(String dia, LocalTime inicio, LocalTime fin) {

        if (fin.isBefore(inicio) || fin.equals(inicio)) {
            return false;
        }

        // Validar solapamiento
        for (HorarioEstudio h : horarios) {
            if (h.getDia().equalsIgnoreCase(dia)) {
                if (!(fin.isBefore(h.getInicio()) || inicio.isAfter(h.getFin()))) {
                    return false;
                }
            }
        }

        HorarioEstudio horario = new HorarioEstudio(dia, inicio, fin);
        horarios.add(horario);

        historial.add("Horario agregado: " + dia + " " + inicio + "-" + fin);
        return true;
    }

    public ArrayList<HorarioEstudio> getHorarios() {
        return horarios;
    }

    // =========================
    // HISTORIAL
    // =========================
    public ArrayList<String> getHistorial() {
        return historial;
    }

    // =========================
    // BUSQUEDAS
    // =========================
    public ArrayList<Tarea> buscarPorMateria(String nombreMateria) {
        ArrayList<Tarea> res = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getMateria().getNombre().equalsIgnoreCase(nombreMateria)) {
                res.add(t);
            }
        }
        return res;
    }

    public ArrayList<Tarea> buscarPorTipo(String tipo) {
        ArrayList<Tarea> res = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getTipo().equalsIgnoreCase(tipo)) {
                res.add(t);
            }
        }
        return res;
    }

    public ArrayList<Tarea> buscarPorPrioridad(int prioridad) {
        ArrayList<Tarea> res = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getPrioridad() == prioridad) {
                res.add(t);
            }
        }
        return res;
    }

    public ArrayList<Tarea> buscarPorDescripcion(String texto) {
        ArrayList<Tarea> res = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getDescripcion().toLowerCase().contains(texto.toLowerCase())) {
                res.add(t);
            }
        }
        return res;
    }

    // =========================
    // AGENDA
    // =========================
    public ArrayList<BloqueAgenda> generarAgenda(LocalDate inicio, LocalDate fin) {

        ArrayList<BloqueAgenda> agenda = new ArrayList<>();

        // Ordenar tareas por fecha y prioridad
        tareas.sort(Comparator.comparing(Tarea::getFechaEntrega).thenComparing(Tarea::getPrioridad));

        for (LocalDate fecha = inicio; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {

            // agregar bloque de fecha
            LocalDate fechaActual = null;
            agenda.add(new BloqueAgenda("---- " + fecha + " ----", "No cabe la tarea en este horario", fechaActual, 1));

            for (Tarea t : tareas) {
                if (t.getFechaEntrega().equals(fecha) || t.getFechaEntrega().isAfter(fecha)) {
                    agenda.add(new BloqueAgenda(
                            t.getDescripcion() + " | " + t.getMateria().getNombre() +
                                    " | " + t.getTipo() +
                                    " | Prioridad: " + t.getPrioridad(),
                            "No cabe la tarea en este horario", fechaActual, 1));
                }
            }
        }

        return agenda;
    }

    // =========================
    // RECOMENDADOR
    // =========================
    public String recomendarTecnica(Tarea tarea) {

        if (tarea == null) {
            return "Tarea inválida.";
        }

        int complejidad = tarea.getComplejidad();
        int tiempoMinutos = tarea.getTiempo();
        String tipo = tarea.getTipo().toLowerCase();

        String tecnica = "Técnica recomendada: ";

        if ((tipo.contains("examen") || tipo.contains("proyecto")) && complejidad >= 7) {
            tecnica += "Pomodoro + Mapas mentales";
        } else if (tipo.contains("lectura") || tipo.contains("investigación")) {
            tecnica += "SQ3R (Survey, Question, Read, Recite, Review)";
        } else if (complejidad <= 4 && tiempoMinutos <= 30) {
            tecnica += "Bloques cortos + Revisión rápida";
        } else if (tiempoMinutos >= 60) {
            tecnica += "Pomodoro + Planificación por etapas";
        } else {
            tecnica += "Revisión activa + Resumen";
        }

        String detalle = "\n\nDetalles:\n" +
                "- Complejidad: " + complejidad + "\n" +
                "- Tiempo estimado: " + tiempoMinutos + " minutos\n" +
                "- Tipo de tarea: " + tarea.getTipo();

        return tecnica + detalle;
    }
}
