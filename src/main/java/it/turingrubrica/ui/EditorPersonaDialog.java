package it.turingrubrica.ui;

import it.turingrubrica.app.domain.Persona;

import javax.swing.*;
import java.awt.*;

public class EditorPersonaDialog extends JDialog {
    public final JTextField tfNome = new JTextField(20);
    public final JTextField tfCognome = new JTextField(20);
    public final JTextField tfIndirizzo = new JTextField(25);
    public final JTextField tfTelefono = new JTextField(15);
    public final JTextField tfEta = new JTextField(5);

    // Gli stessi bottoni esistenti: li mettiamo nella toolbar
    public final JButton btnSalva = new JButton("Salva");
    public final JButton btnAnnulla = new JButton("Annulla");

    private boolean salvato = false;

    public EditorPersonaDialog(Window owner, String titolo) {
        super(owner, titolo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        // === TOOLBAR IN ALTO (richiesta) ===
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

        // (Opzionale) icone bianche 20x20 se hai la classe Icons e i png
        // btnSalva.setIcon(Icons.loadScaled("icons/save.png", 20, 20));
        // btnAnnulla.setIcon(Icons.loadScaled("icons/cancel.png", 20, 20));

        // Aggiungo i bottoni ESISTENTI direttamente nella toolbar
        toolBar.add(btnAnnulla);
        toolBar.add(btnSalva);
        add(toolBar, BorderLayout.NORTH);

        // === FORM CENTRALE (immutato) ===
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 10, 6, 10);
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0; gc.gridy = 0;
        form.add(new JLabel("Nome:"), gc);
        gc.gridy++; form.add(new JLabel("Cognome:"), gc);
        gc.gridy++; form.add(new JLabel("Indirizzo:"), gc);
        gc.gridy++; form.add(new JLabel("Telefono:"), gc);
        gc.gridy++; form.add(new JLabel("Età:"), gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1; gc.gridy = 0;
        form.add(tfNome, gc);
        gc.gridy++; form.add(tfCognome, gc);
        gc.gridy++; form.add(tfIndirizzo, gc);
        gc.gridy++; form.add(tfTelefono, gc);
        gc.gridy++; form.add(tfEta, gc);

        add(form, BorderLayout.CENTER);

        // ❌ RIMOSSA la barra bottoni in basso per aderire alla consegna
        // (Se preferisci tenerla, decommenta il blocco sotto)
        /*
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnAnnulla);
        buttons.add(btnSalva);
        add(buttons, BorderLayout.SOUTH);
        */

        pack();
        setMinimumSize(new Dimension(420, getHeight()));
        setLocationRelativeTo(owner);

        // Logica immutata
        btnSalva.addActionListener(e -> { salvato = true; dispose(); });
        btnAnnulla.addActionListener(e -> { salvato = false; dispose(); });
    }

    public void clearFields() {
        tfNome.setText(""); tfCognome.setText("");
        tfIndirizzo.setText(""); tfTelefono.setText(""); tfEta.setText("");
    }

    public void loadFrom(Persona p) {
        tfNome.setText(p.getNome()); tfCognome.setText(p.getCognome());
        tfIndirizzo.setText(p.getIndirizzo());
        tfTelefono.setText(p.getTelefono());
        tfEta.setText(String.valueOf(p.getEta()));
    }

    public Persona toPersona() {
        int eta = 0; try { eta = Integer.parseInt(tfEta.getText().trim()); } catch (Exception ignored) {}
        return new Persona(
                tfNome.getText().trim(),
                tfCognome.getText().trim(),
                tfIndirizzo.getText().trim(),
                tfTelefono.getText().trim(),
                eta
        );
    }

    public boolean isSalvato() { return salvato; }
}
