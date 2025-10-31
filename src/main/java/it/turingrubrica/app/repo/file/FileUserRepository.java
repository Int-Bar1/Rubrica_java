package it.turingrubrica.app.repo.file;
import it.turingrubrica.app.domain.Utente;
import it.turingrubrica.app.repo.UserRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

//Repo utenti su files (opz)
public class FileUserRepository implements UserRepository {
    private static final String DIR = "users";
    private final Path dir;

    public FileUserRepository() {
        this.dir = Paths.get(DIR);
        try { Files.createDirectories(dir); } catch (IOException ignored) {}
    }

    @Override public Utente findByUsername(String username) {
        Path file = dir.resolve(username + ".properties");
        if (!Files.exists(file)) return null;
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(file));
            return new Utente(username, props.getProperty("passwordHash", ""));
        } catch (IOException e) {
            return null;
        }
    }
}
