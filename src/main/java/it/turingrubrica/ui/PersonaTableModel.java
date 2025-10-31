package it.turingrubrica.ui;

import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.service.RubricaService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonaTableModel extends AbstractTableModel {
    private static final String[] COLS = {"Nome", "Cognome", "Telefono"};
    private final RubricaService service;

    public PersonaTableModel(RubricaService service) { this.service = service; }

    private List<Persona> data() { return service.list(); }

    @Override public int getRowCount() { return data().size(); }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int column) { return COLS[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Persona p = data().get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getNome();
            case 1 -> p.getCognome();
            case 2 -> p.getTelefono();
            default -> "";
        };
    }

    public Persona getPersonaAt(int rowIndex) { return data().get(rowIndex); }
    public void refresh() { fireTableDataChanged(); }
}