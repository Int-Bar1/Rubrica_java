package it.turingrubrica.ui;
import it.turingrubrica.app.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private final JTextField tfUser = new JTextField(16);
    private final JPasswordField tfPass = new JPasswordField(16);
    private final JButton btnLogin = new JButton("LOGIN");
    private boolean authenticated = false;

    public LoginDialog(Window owner, AuthService auth) {
        super(owner, "Login", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0; gc.gridy = 0; form.add(new JLabel("Utente:"), gc);
        gc.gridy++; form.add(new JLabel("Password:"), gc);

        gc.anchor = GridBagConstraints.LINE_START; gc.gridx = 1; gc.gridy = 0;
        form.add(tfUser, gc);
        gc.gridy++; form.add(tfPass, gc);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnLogin);

        add(form, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String p = new String(tfPass.getPassword());
            if (auth.login(u, p)) {
                authenticated = true; dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
                tfPass.setText("");
            }
        });

        getRootPane().setDefaultButton(btnLogin);
        pack();
        setMinimumSize(new Dimension(360, getHeight()));
        setLocationRelativeTo(owner);
    }

    public boolean isAuthenticated() { return authenticated; }
}