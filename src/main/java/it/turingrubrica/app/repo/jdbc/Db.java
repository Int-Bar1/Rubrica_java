package it.turingrubrica.app.repo.jdbc;

        import java.io.FileInputStream;
        import java.nio.file.*;
        import java.sql.*;
        import java.util.Properties;

public final class Db {
    private Db() {}

    public static Connection open() throws Exception {
        Properties p = new Properties();
        Path propsPath = Paths.get("credenziali_database.properties");

        if (!Files.exists(propsPath))
            throw new RuntimeException("File credenziali_database.properties non trovato!");

        try (FileInputStream in = new FileInputStream(propsPath.toFile())) {
            p.load(in);
        }

        String host = p.getProperty("db.host", "127.0.0.1");
        String port = p.getProperty("db.port", "3306");
        String name = p.getProperty("db.name", "rubrica");
        String user = p.getProperty("db.user", "root");
        String pass = p.getProperty("db.password", "TUA_PASS");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + name +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        return DriverManager.getConnection(url, user, pass);
    }
}
