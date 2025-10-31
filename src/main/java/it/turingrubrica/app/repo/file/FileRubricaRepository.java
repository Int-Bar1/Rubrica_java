package it.turingrubrica.app.repo.file;

import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.repo.RubricaRepository;

import java.io.*;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.*;

/**
 * Repo file-based: directory "informazioni" nella working dir.
 * - Un file .txt per persona (nome file = UUID.txt, così evitiamo collisioni).
 * - Carica tutto all'avvio.
 * - Su update/remove tocca solo il file relativo alla riga.
 */
public class FileRubricaRepository implements RubricaRepository {
    private static final String DIR_NAME = "informazioni";
    private static final String EXT = ".txt";

    private final Path dir;
    private final List<Persona> cache = new ArrayList<>();
    private final List<Path> files = new ArrayList<>();

    public FileRubricaRepository() {
        this.dir = Paths.get(DIR_NAME);
        try { Files.createDirectories(dir); } catch (IOException ignored) {}
        loadAll();
    }
//  Lettura / scrittura

    private Persona readPersona(Path file) {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
            return new Persona(
                    props.getProperty("nome", ""),
                    props.getProperty("cognome", ""),
                    props.getProperty("indirizzo", ""),
                    props.getProperty("telefono", ""),
                    Integer.parseInt(props.getProperty("eta", "0"))
            );
        } catch (Exception e) {
            return null;
        }
    }

    private void writePersona(Persona p, Path file) throws IOException {
        Properties props = new Properties();
        props.setProperty("nome", nvl(p.getNome()));
        props.setProperty("cognome", nvl(p.getCognome()));
        props.setProperty("indirizzo", nvl(p.getIndirizzo()));
        props.setProperty("telefono", nvl(p.getTelefono()));
        props.setProperty("eta", String.valueOf(p.getEta()));
        try (OutputStream out = Files.newOutputStream(file)) {
            props.store(out, "Persona");
        }
    }

    private static String nvl(String s) { return s == null ? "" : s; }

    //Generazione nome file

    private String slug(String s) {
        // Rimuove accenti, caratteri non alfanumerici, sostituisce spazi con '-'
        String norm = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return norm.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    private Path buildFileName(Persona p) {
        String base = slug(p.getNome()) + "-" + slug(p.getCognome());
        Path candidate = dir.resolve(base + EXT);
        int i = 2;
        while (Files.exists(candidate)) {
            candidate = dir.resolve(base + "(" + i + ")" + EXT);
            i++;
        }
        return candidate;
    }

    //Caricamen.

    private void loadAll() {
        cache.clear();
        files.clear();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*" + EXT)) {
            for (Path p : ds) {
                Persona persona = readPersona(p);
                if (persona != null) {
                    cache.add(persona);
                    files.add(p);
                }
            }
        } catch (IOException ignored) {}
    }

    //Interfaccia

    @Override
    public List<Persona> findAll() { return Collections.unmodifiableList(cache); }

    @Override
    public Persona findByTelefono(String telefono) {
        if (telefono == null) return null;
        for (Persona p : cache)
            if (telefono.equals(p.getTelefono())) return p;
        return null;
    }

    @Override
    public int size() { return cache.size(); }

    @Override
    public Persona get(int index) { return cache.get(index); }

    @Override
    public void add(Persona p) {
        Path f = buildFileName(p);
        try {
            writePersona(p, f);
            cache.add(p);
            files.add(f);
        } catch (IOException ignored) {}
    }

    @Override
    public void update(int index, Persona p) {
        if (index < 0 || index >= cache.size()) return;
        Path f = files.get(index);
        try {
            writePersona(p, f);
            cache.set(index, p);
        } catch (IOException ignored) {}
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= cache.size()) return;
        Path f = files.get(index);
        try { Files.deleteIfExists(f); } catch (IOException ignored) {}
        files.remove(index);
        cache.remove(index);
    }}
