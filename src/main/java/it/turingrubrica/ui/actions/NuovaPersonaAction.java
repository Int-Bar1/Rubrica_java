package it.turingrubrica.ui.actions;
import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.domain.validation.ValidationException;
import it.turingrubrica.app.service.RubricaService;
import it.turingrubrica.ui.EditorPersonaDialog;
import it.turingrubrica.ui.PersonaTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NuovaPersonaAction extends AbstractAction {
    private final JFrame owner;
    private final RubricaService service;
    private final PersonaTableModel model;

    public NuovaPersonaAction(JFrame owner, RubricaService service, PersonaTableModel model) {
        super("Nuovo");
        putValue(SMALL_ICON, it.turingrubrica.ui.Icons.loadScaled("icons/add.png",    40, 40));           this.owner = owner; this.service = service; this.model = model;
    }

    @Override public void actionPerformed(ActionEvent e) {
        EditorPersonaDialog dlg = new EditorPersonaDialog(owner, "Nuova persona");
        dlg.clearFields();
        dlg.setVisible(true);
        if (dlg.isSalvato()) {
            Persona p = dlg.toPersona();
            try { service.create(p); model.refresh(); }
            catch (ValidationException ex) { JOptionPane.showMessageDialog(owner, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE); }
        }
    }
}