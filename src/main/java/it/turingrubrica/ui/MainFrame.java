package it.turingrubrica.ui;

import it.turingrubrica.app.service.RubricaService;
import it.turingrubrica.ui.actions.EliminaPersonaAction;
import it.turingrubrica.ui.actions.ModificaPersonaAction;
import it.turingrubrica.ui.actions.NuovaPersonaAction;
import it.turingrubrica.ui.selection.SelectionProvider;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements SelectionProvider {
    private final RubricaService service;
    private final PersonaTableModel tableModel;
    private final JTable table;

    // <<< NUOVO: riferimento stabile alla toolbar
    private JToolBar toolBar;

    public MainFrame(RubricaService service) {
        super("Rubrica telefonica");
        this.service = service;
        this.tableModel = new PersonaTableModel(service);
        this.table = new JTable(tableModel);
        initUI();
        wireActions();
    }

    private void initUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // Tabella al centro
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // <<< NUOVO: crea e salva la toolbar in un campo
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        setSize(800, 450);
        setLocationRelativeTo(null);
    }

    private void wireActions() {
        var aNuovo = new NuovaPersonaAction(this, service, tableModel);
        var aMod   = new ModificaPersonaAction(this, service, tableModel, this);
        var aDel   = new EliminaPersonaAction(this, service, tableModel, this);

        // <<< CAMBIATO: usa il campo toolBar, niente cast/indice
        toolBar.add(aNuovo);
        toolBar.add(aMod);
        toolBar.add(aDel);

        // Scorciatoie
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ctrl N"), "nuovo");
        getRootPane().getActionMap().put("nuovo", aNuovo);
    }

    @Override
    public int getSelectedModelRow() {
        int viewRow = table.getSelectedRow();
        return viewRow < 0 ? -1 : table.convertRowIndexToModel(viewRow);
    }
}
