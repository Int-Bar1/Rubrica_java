package it.turingrubrica.app.repo;

import it.turingrubrica.app.domain.Persona;
import java.util.List;

public interface RubricaRepository {
    List<Persona> findAll();
    Persona findByTelefono(String telefono);

    int size();
    Persona get(int index);

    void add(Persona p);
    void update(int index, Persona p);
    void remove(int index);
}