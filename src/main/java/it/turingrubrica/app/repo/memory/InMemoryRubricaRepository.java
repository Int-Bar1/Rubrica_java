package it.turingrubrica.app.repo.memory;
import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.repo.RubricaRepository;

import java.util.*;

public class InMemoryRubricaRepository implements RubricaRepository {
    private final Vector<Persona> data = new Vector<>();


    public InMemoryRubricaRepository() { seedDemo(); }


    private void seedDemo() {
        data.add(new Persona("Steve", "Jobs", "Via Cupertino 13", "0612344", 56));
        data.add(new Persona("Bill", "Gates", "Via Redmond 10", "+06688989", 60));
        data.add(new Persona("Babbo", "Natale", "Via del Polo Nord", "00000111", 99));
    }


    @Override public List<Persona> findAll() { return Collections.unmodifiableList(data); }
    @Override public Persona findByTelefono(String telefono) {
        if (telefono == null) return null;
        for (Persona p : data) if (telefono.equals(p.getTelefono())) return p;
        return null;
    }
    @Override public int size() { return data.size(); }
    @Override public Persona get(int index) { return data.get(index); }
    @Override public void add(Persona p) { data.add(p); }
    @Override public void update(int index, Persona p) { data.set(index, p); }
    @Override public void remove(int index) { data.remove(index); }
}