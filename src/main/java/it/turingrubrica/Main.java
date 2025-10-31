package it.turingrubrica;

import it.turingrubrica.app.domain.validation.PersonaValidator;
import it.turingrubrica.app.repo.RubricaRepository;
import it.turingrubrica.app.repo.UserRepository;
import it.turingrubrica.app.repo.memory.InMemoryRubricaRepository;
import it.turingrubrica.app.repo.memory.InMemoryUserRepository;
import it.turingrubrica.app.service.AuthService;
import it.turingrubrica.app.service.RubricaService;
import it.turingrubrica.ui.LoginDialog;
import it.turingrubrica.ui.MainFrame;
import it.turingrubrica.app.repo.file.FileRubricaRepository;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            //darkmode con nimbus

            try {
                // 1) Attiva Nimbus
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }

                // 2) Palette scura fondamentale
                Color bg      = new Color(0x1f1f1f); // background finestre
                Color panel   = new Color(0x242424); // pannelli
                Color ctrl    = new Color(0x2b2b2b); // componenti
                Color text    = new Color(0xE8E8E8); // testo
                Color muted   = new Color(0xB0B0B0); // testo disabilitato
                Color selBg   = new Color(0x2f4aa0); // selezione
                Color grid    = new Color(0x3a3a3a); // griglia tabella

                UIManager.put("control",                ctrl);
                UIManager.put("info",                   panel);
                UIManager.put("nimbusBase",             new Color(0x2a2a2a));
                UIManager.put("nimbusBlueGrey",         new Color(0x3a3a3a));
                UIManager.put("nimbusLightBackground",  panel);

                UIManager.put("text",                   text);
                UIManager.put("controlText",            text);
                UIManager.put("infoText",               text);
                UIManager.put("menuText",               text);
                UIManager.put("textForeground",         text);
                UIManager.put("textInactiveText",       muted);
                UIManager.put("nimbusSelectedText",     text);
                UIManager.put("nimbusSelectionBackground", selBg);

                // JTable & scroll
                UIManager.put("Table.background",           panel);
                UIManager.put("Table.foreground",           text);
                UIManager.put("Table.selectionBackground",  selBg);
                UIManager.put("Table.selectionForeground",  text);
                UIManager.put("Table.gridColor",            grid);
                UIManager.put("ScrollPane.background",      panel);
                UIManager.put("Viewport.background",        panel);

                // Toolbar & bottoni
                UIManager.put("ToolBar.background",         bg);
                UIManager.put("ToolBar.foreground",         text);
                UIManager.put("Button.background",          ctrl);
                UIManager.put("Button.foreground",          text);

            } catch (Exception ignored) {}


//disattivo qui la rubrica locale
            //RubricaRepository rubricaRepo = new FileRubricaRepository();

            //rubrica su databas
            RubricaRepository rubricaRepo = new it.turingrubrica.app.repo.jdbc.JdbcRubricaRepository();
            UserRepository userRepo = new InMemoryUserRepository();

            PersonaValidator validator = new PersonaValidator();
            RubricaService rubricaService = new RubricaService(rubricaRepo, validator);
            AuthService authService = new AuthService(userRepo);

// Mostra SOLO login all'avvio (req.2)
            LoginDialog login = new LoginDialog(null, authService);
            login.setVisible(true);
            if (!login.isAuthenticated()) {
// in caso di login fallito o si chiuda non passa a main wind.
                System.exit(0);
            }

            MainFrame frame = new MainFrame(rubricaService);
            frame.setVisible(true);
        });
    }
}