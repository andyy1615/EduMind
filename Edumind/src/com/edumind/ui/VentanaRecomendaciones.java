package com.edumind.ui;

import com.edumind.datos.Tarea;
import com.edumind.negocio.GestorAcademico;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaRecomendaciones extends JFrame {

    private GestorAcademico gestor;
    private JComboBox<String> cbTareas;
    private JTextArea txtRecomendacion;

    public VentanaRecomendaciones(GestorAcademico gestor) {
        this.gestor = gestor;
        initComponents();
    }

    private void initComponents() {
        setTitle("Recomendador de Técnicas - EduMind");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel top = new JPanel(new FlowLayout());
        JLabel lbl = new JLabel("Seleccione una tarea:");
        cbTareas = new JComboBox<>();

        top.add(lbl);
        top.add(cbTareas);

        JButton btnRecomendar = new JButton("Recomendar técnica");
        btnRecomendar.addActionListener(e -> recomendar());
        top.add(btnRecomendar);

        add(top, BorderLayout.NORTH);

        // Área de texto para mostrar la recomendación
        txtRecomendacion = new JTextArea();
        txtRecomendacion.setEditable(false);
        txtRecomendacion.setLineWrap(true);
        txtRecomendacion.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtRecomendacion);
        add(scroll, BorderLayout.CENTER);

        cargarTareas();
    }

    private void cargarTareas() {
        cbTareas.removeAllItems();
        ArrayList<Tarea> tareas = gestor.getListaTareas();
        for (Tarea t : tareas) {
            cbTareas.addItem(t.getDescripcion());
        }
    }

    private void recomendar() {
        int index = cbTareas.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "No hay tareas disponibles.");
            return;
        }

        Tarea tarea = gestor.getTareaPorIndice(index);
        if (tarea == null) {
            JOptionPane.showMessageDialog(this, "Tarea no encontrada.");
            return;
        }

        String recomendacion = gestor.recomendarTecnica(tarea);
        txtRecomendacion.setText(recomendacion);
    }
}
