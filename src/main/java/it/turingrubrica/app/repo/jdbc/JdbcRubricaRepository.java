package it.turingrubrica.app.repo.jdbc;

import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.repo.RubricaRepository;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRubricaRepository implements RubricaRepository {

    @Override
    public List<Persona> findAll() {
        String sql = "SELECT nome, cognome, indirizzo, telefono, eta FROM persone ORDER BY cognome, nome";
        List<Persona> list = new ArrayList<>();
        try (Connection c = Db.open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")
                ));
            }
        } catch (Exception ignored) {}
        return list;
    }

    @Override
    public Persona findByTelefono(String tel) {
        if (tel == null || tel.isBlank()) return null;
        String sql = "SELECT nome, cognome, indirizzo, telefono, eta FROM persone WHERE telefono = ?";
        try (Connection c = Db.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tel);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Persona(
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("indirizzo"),
                            rs.getString("telefono"),
                            rs.getInt("eta")
                    );
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    @Override
    public int size() { return findAll().size(); } // semplice

    @Override
    public Persona get(int index) {
        var all = findAll();
        return (index < 0 || index >= all.size()) ? null : all.get(index);
    }

    @Override
    public void add(Persona p) {
        String sql = "INSERT INTO persone(nome, cognome, indirizzo, telefono, eta) VALUES (?,?,?,?,?)";
        try (Connection c = Db.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getEta());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Errore database:\n" + e.getMessage(),
                            "DB error", JOptionPane.ERROR_MESSAGE)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(int index, Persona p) {
        // telefono originale
        Persona original = get(index);
        if (original == null) return;
        String oldTel = original.getTelefono();

        String sql = "UPDATE persone " +
                "SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? " +
                "WHERE telefono=?"; // usa il vecchio telefono
        try (Connection c = Db.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());  // <-- nuovo telefono
            ps.setInt(5, p.getEta());
            ps.setString(6, oldTel);           // <-- vecchio telefono
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void remove(int index) {
        Persona p = get(index);
        if (p == null) return;
        String sql = "DELETE FROM persone WHERE telefono = ?";
        try (Connection c = Db.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getTelefono());
            ps.executeUpdate();
        } catch (Exception ignored) {}
    }
}
