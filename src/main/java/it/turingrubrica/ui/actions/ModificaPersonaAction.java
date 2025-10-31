package it.turingrubrica.ui.actions;
import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.domain.validation.ValidationException;
import it.turingrubrica.app.service.RubricaService;
import it.turingrubrica.ui.EditorPersonaDialog;
import it.turingrubrica.ui.PersonaTableModel;
import it.turingrubrica.ui.selection.SelectionProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ModificaPersonaAction extends AbstractAction {
    private final JFrame owner;
    private final RubricaService service;
    private final PersonaTableModel model;
    private final SelectionProvider selection;

    public ModificaPersonaAction(JFrame owner, RubricaService service, PersonaTableModel model, SelectionProvider selection) {
        super("Modifica");
        putValue(SMALL_ICON, it.turingrubrica.ui.Icons.loadScaled("icons/edit.png",   40, 40));        this.owner = owner; this.service = service; this.model = model; this.selection = selection;
    }

    @Override public void actionPerformed(ActionEvent e) {
        int row = selection.getSelectedModelRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(owner, "Seleziona prima una persona da modificare.", "Nessuna selezione", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Persona p = model.getPersonaAt(row);
        EditorPersonaDialog dlg = new EditorPersonaDialog(owner, "Modifica persona");
        dlg.loadFrom(p);
        dlg.setVisible(true);
        if (dlg.isSalvato()) {
            try { service.update(row, dlg.toPersona()); model.refresh(); }
            catch (ValidationException ex) { JOptionPane.showMessageDialog(owner, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE); }
        }
    }
}