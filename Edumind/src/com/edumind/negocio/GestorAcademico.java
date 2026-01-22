package com.edumind.negocio;

import com.edumind.datos.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class GestorAcademico {

    private ArrayList<Materia> listaMaterias;
    private ArrayList<Tarea> listaTareas;
    private ArrayList<HorarioEstudio> listaHorarios;
    private ArrayList<String> historial;

    private RepositorioTecnicas repoTecnicas;
    private GestorPrioridades gestorPrioridades;

    public GestorAcademico() {
        listaMaterias = new ArrayList<>();
        listaTareas = new ArrayList<>();
        listaHorarios = new ArrayList<>();
        historial = new ArrayList<>();

        repoTecnicas = new RepositorioTecnicas();
        gestorPrioridades = new GestorPrioridades();
    }

    // =========================
    // MATERIAS
    // =========================
    public void agregarMateria(Materia m) {
        listaMaterias.add(m);
        historial.add("Materia agregada: " + m.getNombre());
    }

    public ArrayList<Materia> getListaMaterias() {
        return listaMaterias;
    }

    // =========================
    // TAREAS
    // =========================
    public void agregarTarea(Tarea t) {

        // CALCULAR PRIORIDAD AQUÍ
        int prioridad = gestorPrioridades.calcularPrioridad(t);
        t.setPrioridad(prioridad);

        listaTareas.add(t);
        historial.add("Tarea agregada: " + t.getDescripcion());
    }

    public ArrayList<Tarea> getListaTareas() {
        return listaTareas;
    }

    public Tarea getTareaPorIndice(int index) {
        if (index < 0 || index >= listaTareas.size()) {
            return null;
        }
        return listaTareas.get(index);
    }

    // =========================
    // HORARIOS
    // =========================
    public boolean agregarHorario(String dia, LocalTime inicio, LocalTime fin) {

        if (fin.isBefore(inicio) || fin.equals(inicio)) {
            return false;
        }

        for (HorarioEstudio h : listaHorarios) {
            if (h.getDia().equalsIgnoreCase(dia)) {
                if (!(fin.isBefore(h.getInicio()) || inicio.isAfter(h.getFin()))) {
                    return false;
                }
            }
        }

        listaHorarios.add(new HorarioEstudio(dia, inicio, fin));
        historial.add("Horario agregado: " + dia + " " + inicio + " - " + fin);
        return true;
    }

    public ArrayList<HorarioEstudio> getHorarios() {
        return listaHorarios;
    }

    // =========================
    // HISTORIAL
    // =========================
    public ArrayList<String> getHistorial() {
        return historial;
    }

    // =========================
    // AGENDA
    // =========================
    public ArrayList<BloqueAgenda> generarAgenda(ArrayList<HorarioEstudio> horarios, LocalDate inicio, LocalDate fin) {

        // ASIGNAR TÉCNICA A TODAS LAS TAREAS ANTES DE GENERAR AGENDA
        for (Tarea t : listaTareas) {
            if (t.getTecnicaRecomendada() == null || t.getTecnicaRecomendada().isEmpty()) {
                recomendarTecnica(t);
            }
        }

        PriorityQueue<Tarea> cola = new PriorityQueue<>(listaTareas);
        PlanificadorEstudio planificador = new PlanificadorEstudio();
        return planificador.generarAgenda(cola, horarios, inicio, fin, this);
    }

    // =========================
    // RECOMENDADOR
    // =========================
    public TecnicaEstudio recomendarTecnica(Tarea tarea) {

        // Si ya tiene técnica asignada, devuelve la misma del repositorio
        if (tarea.getTecnicaRecomendada() != null && !tarea.getTecnicaRecomendada().isEmpty()) {
            return repoTecnicas.obtenerTecnica(tarea.getPrioridad());
        }

        // Usa repositorio de técnicas
        int prioridad = tarea.getPrioridad();
        TecnicaEstudio tecnica = repoTecnicas.obtenerTecnica(prioridad);

        // Guarda técnica y detalles en la tarea
        tarea.setTecnicaRecomendada(tecnica.getNombre());
        tarea.setDescripcionTecnica(tecnica.getDescripcion());
        tarea.setPasosTecnica(tecnica.getPasos());
        tarea.setCuandoUsar(tecnica.getCuandoUsar());

        return tecnica;
    }
}
