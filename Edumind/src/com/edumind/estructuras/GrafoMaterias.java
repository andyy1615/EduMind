package com.edumind.estructuras;

import java.util.*;

public class GrafoMaterias {

    private Map<String, List<String>> relaciones = new HashMap<>();

    public void agregarMateria(String nombre) {
        relaciones.putIfAbsent(nombre, new ArrayList<>());
    }

    public void relacionar(String origen, String destino) {
        relaciones.putIfAbsent(origen, new ArrayList<>());
        relaciones.putIfAbsent(destino, new ArrayList<>());
        relaciones.get(origen).add(destino);
    }

    public List<String> obtenerRelaciones(String materia) {
        return relaciones.getOrDefault(materia, new ArrayList<>());
    }
}
