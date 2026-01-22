package com.edumind.ui;

import com.edumind.datos.Materia;
import com.edumind.datos.Tarea;
import com.edumind.negocio.GestorAcademico;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class VentanaAgregarTarea extends JDialog {

    private GestorAcademico gestor;

    private JTextField txtDescripcion;
    private JSpinner spTiempo;
    private JSpinner spComplejidad;
    private JComboBox<Materia> cbMaterias;
    private JTextField txtTipo;
    private JSpinner spFechaEntrega; // <-- fecha con JSpinner

    public VentanaAgregarTarea(JFrame parent, GestorAcademico gestor) {
        super(parent, "Agregar tarea", true);
        this.gestor = gestor;
        initUI();
    }

    private void initUI() {
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        add(txtDescripcion);

        add(new JLabel("Tiempo (min):"));
        spTiempo = new JSpinner(new SpinnerNumberModel(30, 10, 300, 5));
        add(spTiempo);

        add(new JLabel("Complejidad (1-10):"));
        spComplejidad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        add(spComplejidad);

        add(new JLabel("Materia:"));
        cbMaterias = new JComboBox<>();
        add(cbMaterias);

        add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        add(txtTipo);

        add(new JLabel("Fecha entrega:"));
        spFechaEntrega = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spFechaEntrega, "yyyy-MM-dd");
        spFechaEntrega.setEditor(dateEditor);
        add(spFechaEntrega);

        JButton btnOK = new JButton("Agregar");
        btnOK.addActionListener(e -> agregarTarea());
        add(btnOK);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);

        cargarMaterias();
    }

    private void cargarMaterias() {
        cbMaterias.removeAllItems();
        for (Materia m : gestor.getListaMaterias()) {
            cbMaterias.addItem(m);
        }
    }

    private void agregarTarea() {
        String descripcion = txtDescripcion.getText();
        int tiempo = (int) spTiempo.getValue();
        int complejidad = (int) spComplejidad.getValue();
        Materia materia = (Materia) cbMaterias.getSelectedItem();
        String tipo = txtTipo.getText();

        // convertir Date a LocalDate
        Date fechaDate = (Date) spFechaEntrega.getValue();
        LocalDate fechaEntrega = fechaDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Tarea tarea = new Tarea(descripcion, fechaEntrega, complejidad, tiempo, materia, tipo);
        gestor.agregarTarea(tarea);

        JOptionPane.showMessageDialog(this, "Tarea agregada correctamente");
        dispose();
    }
}
