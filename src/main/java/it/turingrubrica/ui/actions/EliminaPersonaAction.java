package it.turingrubrica.ui.actions;
import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.service.RubricaService;
import it.turingrubrica.ui.PersonaTableModel;
import it.turingrubrica.ui.selection.SelectionProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EliminaPersonaAction extends AbstractAction {
    private final JFrame owner;
    private final RubricaService service;
    private final PersonaTableModel model;
    private final SelectionProvider selection;

    public EliminaPersonaAction(JFrame owner, RubricaService service, PersonaTableModel model, SelectionProvider selection) {
        super("Elimina");
        putValue(SMALL_ICON, it.turingrubrica.ui.Icons.loadScaled("icons/delete.png", 40, 40));
        this.owner = owner; this.service = service; this.model = model; this.selection = selection;
    }

    @Override public void actionPerformed(ActionEvent e) {
        int row = selection.getSelectedModelRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(owner, "Seleziona prima una persona da eliminare.", "Nessuna selezione", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Persona p = model.getPersonaAt(row);
        int choice = JOptionPane.showConfirmDialog(owner,
                "Eliminare la persona " + p.getNome() + " " + p.getCognome() + "?",
                "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            service.delete(row);
            model.refresh();
        }
    }
}