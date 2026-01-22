package com.edumind.ui;

import com.edumind.negocio.GestorAcademico;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class VentanaAgregarTarea extends JDialog {

    private GestorAcademico gestor;

    public VentanaAgregarTarea(JFrame parent, GestorAcademico gestor) {
        super(parent, true);
        this.gestor = gestor;
        initUI();
    }

    private void initUI() {
        setTitle("Agregar Tarea");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        // 1) Descripción
        add(new JLabel("Descripción:"));
        JTextField txtDescripcion = new JTextField();
        add(txtDescripcion);

        // 2) Materia (ComboBox)
        add(new JLabel("Materia:"));
        JComboBox<String> cbMateria = new JComboBox<>();
        for (String nombre : gestor.getListaMaterias()) {
            cbMateria.addItem(nombre);
        }
        add(cbMateria);

        // 3) Tipo de tarea
        add(new JLabel("Tipo de tarea:"));
        JComboBox<String> cbTipo = new JComboBox<>();
        cbTipo.addItem("Lectura");
        cbTipo.addItem("Ejercicio");
        cbTipo.addItem("Proyecto");
        cbTipo.addItem("Examen");
        cbTipo.addItem("Otro");
        add(cbTipo);

        // 4) Fecha de entrega
        add(new JLabel("Fecha entrega (AAAA-MM-DD):"));
        JTextField txtFecha = new JTextField();
        add(txtFecha);

        // 5) Complejidad (1-5)
        add(new JLabel("Complejidad (1-5):"));
        JSpinner spComplejidad = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        add(spComplejidad);

        // 6) Tiempo estimado (horas)
        add(new JLabel("Tiempo estimado (horas):"));
        JSpinner spTiempo = new JSpinner(new SpinnerNumberModel(1, 1, 24, 1));
        add(spTiempo);

        // Botón agregar
        JButton btnAgregar = new JButton("Agregar Tarea");
        add(btnAgregar);

        // Botón cancelar
        JButton btnCancelar = new JButton("Cancelar");
        add(btnCancelar);

        btnAgregar.addActionListener(e -> {
            String desc = txtDescripcion.getText();
            String materia = (String) cbMateria.getSelectedItem();
            String tipo = (String) cbTipo.getSelectedItem();
            String fechaStr = txtFecha.getText();
            int complejidad = (int) spComplejidad.getValue();
            int tiempo = (int) spTiempo.getValue();

            // Validaciones
            if (desc.isEmpty() || materia == null || fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            try {
                LocalDate fecha = LocalDate.parse(fechaStr);

                boolean ok = gestor.agregarTarea(desc, fecha, complejidad, tiempo, materia, tipo);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Tarea agregada con éxito.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "La materia no existe.");
                }

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Fecha inválida. Use AAAA-MM-DD.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
