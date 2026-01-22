package com.edumind.datos;

import java.util.ArrayList;

public class RepositorioTecnicas {

    private ArrayList<TecnicaEstudio> tecnicas;

    public RepositorioTecnicas() {
        tecnicas = new ArrayList<>();
        cargarTecnicas();
    }

    private void cargarTecnicas() {
        tecnicas.add(new TecnicaEstudio(
                "Pomodoro (25 min)",
                "Técnica para aumentar concentración y reducir distracciones.",
                "1. Trabaja 25 min\n2. Descansa 5 min\n3. Repite 4 veces\n4. Descanso largo",
                "Cuando la tarea es urgente y requiere concentración."
        ));

        tecnicas.add(new TecnicaEstudio(
                "Técnica de Feynman",
                "Explica el tema como si se lo enseñaras a alguien más.",
                "1. Elige el tema\n2. Explica en palabras simples\n3. Identifica dudas\n4. Revisa y repite",
                "Cuando necesitas entender y memorizar conceptos."
        ));

        tecnicas.add(new TecnicaEstudio(
                "Bloques de estudio (45 min)",
                "Divide el tiempo en bloques largos para tareas menos urgentes.",
                "1. Estudia 45 min\n2. Descansa 10 min\n3. Repite",
                "Cuando la tarea no es urgente y necesitas avanzar por tiempo."
        ));
    }

    public TecnicaEstudio obtenerTecnica(int prioridad) {
        if (prioridad >= 8) {
            return tecnicas.get(0); // Pomodoro
        } else if (prioridad >= 5) {
            return tecnicas.get(1); // Feynman
        } else {
            return tecnicas.get(2); // Bloques 45
        }
    }
}
