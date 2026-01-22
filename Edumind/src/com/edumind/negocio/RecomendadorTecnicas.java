package com.edumind.negocio;
import com.edumind.datos.Tarea;

public class RecomendadorTecnicas {

    public String recomendar(Tarea tarea) {

        String tipo = tarea.getTipo().toLowerCase();
        int complejidad = tarea.getComplejidad();

        if (tipo.equals("examen")) {
            return """
                   Técnica: Repaso espaciado
                   Consiste en estudiar en sesiones cortas distribuidas en varios días.
                   Ayuda a retener información a largo plazo.
                   """;
        }

        if (tipo.equals("proyecto")) {
            return """
                   Técnica: Método Pomodoro
                   Trabaja en bloques de 25 minutos con descansos cortos.
                   Ideal para tareas largas y demandantes.
                   """;
        }

        if (tipo.equals("lectura")) {
            return """
                   Técnica: Lectura activa
                   Subrayar, hacer preguntas y resúmenes.
                   Mejora la comprensión del contenido.
                   """;
        }

        if (complejidad >= 8) {
            return """
                   Técnica: Mapas mentales
                   Organiza la información visualmente.
                   Facilita entender temas complejos.
                   """;
        }

        return """
               Técnica: Estudio libre
               Organiza tu tiempo según tu comodidad.
               Útil para tareas simples.
               """;
    }
}
