package com.edumind.datos;

import java.util.ArrayList;

public class Historial {

    private ArrayList<String> actividades = new ArrayList<>();

    public void registrar(String accion) {
        actividades.add(accion);
    }

    public ArrayList<String> getActividades() {
        return actividades;
    }

    public void mostrar() {
        if (actividades.isEmpty()) {
            System.out.println("No hay historial.");
        } else {
            System.out.println("\n===== HISTORIAL =====");
            for (String a : actividades) {
                System.out.println("- " + a);
            }
        }
    }
}
