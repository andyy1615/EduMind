package com.edumind.ui;

import com.edumind.negocio.GestorAcademico;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class VentanaHorario extends JDialog {

    private GestorAcademico gestor;

    public VentanaHorario(JFrame parent, GestorAcademico gestor) {
        super(parent, "Agregar horario de estudio", true);
        this.gestor = gestor;
        initUI();
    }

    private void initUI() {

        setSize(350, 280);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Día
        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Día:"), c);

        JComboBox<String> comboDia = new JComboBox<>(
                new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"}
        );
        c.gridx = 1;
        panel.add(comboDia, c);

        // Hora inicio
        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Hora inicio:"), c);

        JSpinner horaInicio = new JSpinner(new SpinnerNumberModel(8, 0, 23, 1));
        JSpinner minInicio = new JSpinner(new SpinnerNumberModel(0, 0, 59, 5));

        JPanel inicioPanel = new JPanel();
        inicioPanel.add(horaInicio);
        inicioPanel.add(new JLabel(":"));
        inicioPanel.add(minInicio);

        c.gridx = 1;
        panel.add(inicioPanel, c);

        // Hora fin
        c.gridx = 0; c.gridy = 2;
        panel.add(new JLabel("Hora fin:"), c);

        JSpinner horaFin = new JSpinner(new SpinnerNumberModel(9, 0, 23, 1));
        JSpinner minFin = new JSpinner(new SpinnerNumberModel(0, 0, 59, 5));

        JPanel finPanel = new JPanel();
        finPanel.add(horaFin);
        finPanel.add(new JLabel(":"));
        finPanel.add(minFin);

        c.gridx = 1;
        panel.add(finPanel, c);

        add(panel, BorderLayout.CENTER);

        // Botones
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancelar");

        JPanel botones = new JPanel();
        botones.add(btnOK);
        botones.add(btnCancel);

        add(botones, BorderLayout.SOUTH);

        // Eventos
        btnOK.addActionListener(e -> {

            String dia = comboDia.getSelectedItem().toString();

            LocalTime inicio = LocalTime.of(
                    (int) horaInicio.getValue(),
                    (int) minInicio.getValue()
            );

            LocalTime fin = LocalTime.of(
                    (int) horaFin.getValue(),
                    (int) minFin.getValue()
            );

            boolean ok = gestor.agregarHorario(dia, inicio, fin);

            JOptionPane.showMessageDialog(this,
                    ok ? "Horario agregado correctamente" : "Horario inválido o solapado");

            if (ok) dispose();
        });

        btnCancel.addActionListener(e -> dispose());
    }
}
