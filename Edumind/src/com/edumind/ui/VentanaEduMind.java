package com.edumind.ui;

import com.edumind.datos.BloqueAgenda;
import com.edumind.datos.HorarioEstudio;
import com.edumind.negocio.GestorAcademico;
import com.edumind.datos.Tarea;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class VentanaEduMind extends JFrame {

    private GestorAcademico gestor;

    public VentanaEduMind() {
        gestor = new GestorAcademico();
        initComponents();
    }

    private void initComponents() {

        setTitle("EduMind - Agenda Académica Inteligente");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("EduMind", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(3, 2, 15, 15));
        botones.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton btnMateria = new JButton("Agregar Materia");
        JButton btnTarea = new JButton("Agregar Tarea");
        JButton btnBuscar = new JButton("Buscar Tareas");
        JButton btnAgenda = new JButton("Ver Agenda");
        JButton btnHistorial = new JButton("Ver Historial");
        JButton btnHorario = new JButton("Agregar Horario");

        btnMateria.addActionListener(e -> abrirAgregarMateria());
        btnTarea.addActionListener(e -> abrirAgregarTarea());
        btnBuscar.addActionListener(e -> buscarConFiltros());
        btnAgenda.addActionListener(e -> verAgenda());
        btnHistorial.addActionListener(e -> verHistorial());
        btnHorario.addActionListener(e -> {
            new VentanaHorario(this, gestor).setVisible(true);
        });

        botones.add(btnMateria);
        botones.add(btnTarea);
        botones.add(btnBuscar);
        botones.add(btnAgenda);
        botones.add(btnHistorial);
        botones.add(btnHorario);

        panel.add(botones, BorderLayout.CENTER);
        add(panel);
    }

    //MATERIA

    private void abrirAgregarMateria() {

        String nombre = JOptionPane.showInputDialog(this, "Nombre de la materia:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
            return;
        }

        String impTxt = JOptionPane.showInputDialog(this, "Importancia (1 a 5):");
        int importancia;

        try {
            importancia = Integer.parseInt(impTxt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un número");
            return;
        }

        if (importancia < 1 || importancia > 5) {
            JOptionPane.showMessageDialog(this, "La importancia debe estar entre 1 y 5");
            return;
        }

        boolean ok = gestor.agregarMateria(nombre.trim(), importancia);

        JOptionPane.showMessageDialog(this,
                ok ? "Materia agregada correctamente" : "La materia ya existe");
    }

    // TAREA

    private void abrirAgregarTarea() {

        if (gestor.getListaMaterias().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar materias primero");
            return;
        }

        JTextField txtDesc = new JTextField();

        // Fecha visual
        JSpinner spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "yyyy-MM-dd"));

        JTextField txtComplejidad = new JTextField();

        // Tiempo estimado
        JComboBox<String> comboTiempo = new JComboBox<>(new String[]{
                "15 minutos",
                "30 minutos",
                "1 hora",
                "+1 hora"
        });

        JComboBox<String> comboMateria =
                new JComboBox<>(gestor.getListaMaterias().toArray(new String[0]));

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "Lectura",
                "Examen",
                "Proyecto",
                "Investigación"
        });

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDesc);
        panel.add(new JLabel("Fecha entrega:"));
        panel.add(spFecha);
        panel.add(new JLabel("Complejidad (1-10):"));
        panel.add(txtComplejidad);
        panel.add(new JLabel("Tiempo estimado:"));
        panel.add(comboTiempo);
        panel.add(new JLabel("Materia:"));
        panel.add(comboMateria);
        panel.add(new JLabel("Tipo de tarea:"));
        panel.add(comboTipo);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Agregar tarea", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) return;

        // Validaciones
        String desc = txtDesc.getText().trim();
        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción es obligatoria");
            return;
        }

        int complejidad;
        try {
            complejidad = Integer.parseInt(txtComplejidad.getText());
            if (complejidad < 1 || complejidad > 10) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Complejidad inválida");
            return;
        }

        // Tiempo → horas
        int tiempo;
        String tiempoSel = comboTiempo.getSelectedItem().toString();
        switch (tiempoSel) {
            case "15 minutos" -> tiempo = 1;
            case "30 minutos" -> tiempo = 1;
            case "1 hora" -> tiempo = 1;
            default -> tiempo = 2;
        }

        LocalDate fecha = ((java.util.Date) spFecha.getValue())
                .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        boolean ok = gestor.agregarTarea(
                desc,
                fecha,
                complejidad,
                tiempo,
                comboMateria.getSelectedItem().toString(),
                comboTipo.getSelectedItem().toString()
        );

        JOptionPane.showMessageDialog(this,
                ok ? "Tarea agregada correctamente" : "Error al agregar tarea");
    }

    //BUSCADOR
    private void buscarConFiltros() {

        JTextField txtDesc = new JTextField();
        JComboBox<String> comboMateria = new JComboBox<>();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "", "Examen", "Proyecto", "Lectura", "Investigación"
        });
        JTextField txtPrioridad = new JTextField();

        comboMateria.addItem("");
        for (String m : gestor.getListaMaterias()) {
            comboMateria.addItem(m);
        }

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Descripción contiene:"));
        panel.add(txtDesc);
        panel.add(new JLabel("Materia:"));
        panel.add(comboMateria);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Prioridad:"));
        panel.add(txtPrioridad);

        int res = JOptionPane.showConfirmDialog(this, panel,
                "Buscar Tareas", JOptionPane.OK_CANCEL_OPTION);

        if (res != JOptionPane.OK_OPTION) return;

        ArrayList<Tarea> resultados = gestor.getListaTareas();

        if (!txtDesc.getText().isBlank()) {
            resultados.removeIf(t ->
                    !t.getDescripcion().toLowerCase()
                            .contains(txtDesc.getText().toLowerCase()));
        }

        if (!comboMateria.getSelectedItem().toString().isBlank()) {
            resultados.removeIf(t ->
                    !t.getMateria().getNombre()
                            .equalsIgnoreCase(comboMateria.getSelectedItem().toString()));
        }

        if (!comboTipo.getSelectedItem().toString().isBlank()) {
            resultados.removeIf(t ->
                    !t.getTipo().equalsIgnoreCase(comboTipo.getSelectedItem().toString()));
        }

        if (!txtPrioridad.getText().isBlank()) {
            try {
                int p = Integer.parseInt(txtPrioridad.getText());
                resultados.removeIf(t -> t.getPrioridad() != p);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Prioridad inválida");
                return;
            }
        }

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron tareas");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Tarea t : resultados) {
            sb.append("• ")
                    .append(t.getDescripcion())
                    .append(" | ")
                    .append(t.getMateria().getNombre())
                    .append(" | ")
                    .append(t.getTipo())
                    .append(" | Prioridad: ")
                    .append(t.getPrioridad())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(),
                "Resultados", JOptionPane.INFORMATION_MESSAGE);
    }

    //HISTORIAL

    private void verHistorial() {
        var h = gestor.getHistorial();
        JOptionPane.showMessageDialog(this,
                h.isEmpty() ? "No hay historial" : String.join("\n", h));
    }

    //AGENDA
    private void verAgenda() {

        // Selector visual de fecha inicio
        JSpinner spInicio = new JSpinner(new SpinnerDateModel());
        spInicio.setEditor(new JSpinner.DateEditor(spInicio, "yyyy-MM-dd"));

        // Selector visual de fecha fin
        JSpinner spFin = new JSpinner(new SpinnerDateModel());
        spFin.setEditor(new JSpinner.DateEditor(spFin, "yyyy-MM-dd"));

        JPanel panelFechas = new JPanel(new GridLayout(2, 2, 10, 10));
        panelFechas.add(new JLabel("Fecha inicio:"));
        panelFechas.add(spInicio);
        panelFechas.add(new JLabel("Fecha fin:"));
        panelFechas.add(spFin);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                panelFechas,
                "Seleccionar rango de fechas",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        LocalDate inicio = ((java.util.Date) spInicio.getValue())
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        LocalDate fin = ((java.util.Date) spFin.getValue())
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        if (fin.isBefore(inicio)) {
            JOptionPane.showMessageDialog(this,
                    "La fecha fin no puede ser anterior a la fecha inicio");
            return;
        }

        var agenda = gestor.generarAgenda(inicio, fin);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        for (BloqueAgenda linea : agenda) {
            area.append(linea + "\n");
        }

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(520, 400));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Agenda de Estudio",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    //HORARIOS
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaEduMind().setVisible(true));
    }
    private void abrirHorarios() {

        String[] opciones = {
                "Agregar horario",
                "Ver horarios"
        };

        int opcion = JOptionPane.showOptionDialog(this,
                "Seleccione una opción",
                "Horarios de estudio",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (opcion == 0) {
            agregarHorarioUI();
        } else if (opcion == 1) {
            verHorariosUI();
        }
    }
    private void agregarHorarioUI() {

        // Día
        String[] dias = {
                "Lunes", "Martes", "Miércoles",
                "Jueves", "Viernes", "Sábado", "Domingo"
        };

        JComboBox<String> cbDia = new JComboBox<>(dias);

        // Hora inicio
        JComboBox<Integer> cbHoraInicio = new JComboBox<>();
        JComboBox<Integer> cbMinInicio = new JComboBox<>();

        // Hora fin
        JComboBox<Integer> cbHoraFin = new JComboBox<>();
        JComboBox<Integer> cbMinFin = new JComboBox<>();

        for (int h = 0; h < 24; h++) {
            cbHoraInicio.addItem(h);
            cbHoraFin.addItem(h);
        }

        cbMinInicio.addItem(0);
        cbMinInicio.addItem(30);
        cbMinFin.addItem(0);
        cbMinFin.addItem(30);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Día:"));
        panel.add(cbDia);

        panel.add(new JLabel("Hora inicio:"));
        panel.add(cbHoraInicio);
        panel.add(new JLabel("Minutos inicio:"));
        panel.add(cbMinInicio);

        panel.add(new JLabel("Hora fin:"));
        panel.add(cbHoraFin);
        panel.add(new JLabel("Minutos fin:"));
        panel.add(cbMinFin);

        int res = JOptionPane.showConfirmDialog(this,
                panel,
                "Agregar horario de estudio",
                JOptionPane.OK_CANCEL_OPTION);

        if (res != JOptionPane.OK_OPTION) return;

        LocalTime inicio = LocalTime.of(
                (int) cbHoraInicio.getSelectedItem(),
                (int) cbMinInicio.getSelectedItem());

        LocalTime fin = LocalTime.of(
                (int) cbHoraFin.getSelectedItem(),
                (int) cbMinFin.getSelectedItem());

        boolean ok = gestor.agregarHorario(
                (String) cbDia.getSelectedItem(),
                inicio,
                fin
        );

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Horario agregado correctamente");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Horario inválido o solapado");
        }
    }
    private void verHorariosUI() {

        var horarios = gestor.getHorarios();

        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay horarios registrados");
            return;
        }

        StringBuilder sb = new StringBuilder("HORARIOS DE ESTUDIO\n\n");

        for (HorarioEstudio h : horarios) {
            sb.append("• ").append(h).append("\n");
        }

        JOptionPane.showMessageDialog(this,
                sb.toString(),
                "Horarios",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
