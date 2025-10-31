package it.turingrubrica;

import it.turingrubrica.app.repo.jdbc.Db;
import java.sql.*;

public class TestConnessione {
    public static void main(String[] args) {
        try (Connection c = Db.open()) {
            System.out.println("✅ Connessione OK!");
            try (Statement s = c.createStatement();
                 ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM persone")) {
                if (rs.next())
                    System.out.println("Record persone: " + rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
