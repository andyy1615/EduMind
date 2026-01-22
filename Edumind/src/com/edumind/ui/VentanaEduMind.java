package com.edumind.ui;

import com.edumind.datos.Materia;
import com.edumind.datos.Tarea;
import com.edumind.datos.HorarioEstudio;
import com.edumind.datos.BloqueAgenda;
import com.edumind.negocio.GestorAcademico;

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
        btnHorario.addActionListener(e -> new VentanaHorario(this, gestor).setVisible(true));

        botones.add(btnMateria);
        botones.add(btnTarea);
        botones.add(btnBuscar);
        botones.add(btnAgenda);
        botones.add(btnHistorial);
        botones.add(btnHorario);

        panel.add(botones, BorderLayout.CENTER);
        add(panel);
    }

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

        Materia m = new Materia(nombre.trim(), importancia);
        gestor.agregarMateria(m);

        JOptionPane.showMessageDialog(this, "Materia agregada correctamente");
    }

    private void abrirAgregarTarea() {

        if (gestor.getListaMaterias().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar materias primero");
            return;
        }

        JTextField txtDesc = new JTextField();

        JSpinner spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "yyyy-MM-dd"));

        JTextField txtComplejidad = new JTextField();

        JComboBox<String> comboTiempo = new JComboBox<>(new String[]{
                "15 minutos", "30 minutos", "1 hora", "+1 hora"
        });

        JComboBox<Materia> comboMateria =
                new JComboBox<>(gestor.getListaMaterias().toArray(new Materia[0]));

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "Lectura", "Examen", "Proyecto", "Investigación"
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

        int tiempo;
        String tiempoSel = comboTiempo.getSelectedItem().toString();
        switch (tiempoSel) {
            case "15 minutos", "30 minutos", "1 hora" -> tiempo = 1;
            default -> tiempo = 2;
        }

        LocalDate fecha = ((java.util.Date) spFecha.getValue())
                .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        Materia materia = (Materia) comboMateria.getSelectedItem();

        Tarea tarea = new Tarea(desc, fecha, complejidad, tiempo, materia, comboTipo.getSelectedItem().toString());
        gestor.agregarTarea(tarea);

        JOptionPane.showMessageDialog(this, "Tarea agregada correctamente");
    }

    private void buscarConFiltros() {

        JTextField txtDesc = new JTextField();
        JComboBox<String> comboMateria = new JComboBox<>();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "", "Examen", "Proyecto", "Lectura", "Investigación"
        });
        JTextField txtPrioridad = new JTextField();

        comboMateria.addItem("");
        for (Materia m : gestor.getListaMaterias()) {
            comboMateria.addItem(m.getNombre());
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

        ArrayList<Tarea> resultados = new ArrayList<>(gestor.getListaTareas());

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

        // <-- CORRECCIÓN AQUÍ
        var agenda = gestor.generarAgenda(gestor.getHorarios(), inicio, fin);



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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaEduMind().setVisible(true));
    }
}
