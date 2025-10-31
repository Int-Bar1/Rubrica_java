package it.turingrubrica.app.service;
import it.turingrubrica.app.domain.Persona;
import it.turingrubrica.app.domain.validation.PersonaValidator;
import it.turingrubrica.app.domain.validation.ValidationException;
import it.turingrubrica.app.repo.RubricaRepository;

import java.util.List;

public class RubricaService {
    private final RubricaRepository repo;
    private final PersonaValidator validator;

    public RubricaService(RubricaRepository repo, PersonaValidator validator) {
        this.repo = repo; this.validator = validator;
    }

    public List<Persona> list() { return repo.findAll(); }

    public void create(Persona p) throws ValidationException {
        validator.validate(p);
        if (repo.findByTelefono(p.getTelefono()) != null)
            throw new ValidationException("Telefono già presente in rubrica.");
        repo.add(p);
    }

    public void update(int index, Persona p) throws ValidationException {
        validator.validate(p);

        // telefono vecchio della riga selezionata
        Persona original = repo.get(index);
        if (original == null) throw new ValidationException("Record non trovato.");
        String oldTel = original.getTelefono();

        // controllo unicità del nuovo telefono
        Persona conflict = repo.findByTelefono(p.getTelefono());
        if (conflict != null && !p.getTelefono().equals(oldTel)) {
            throw new ValidationException("Telefono già usato da un altro contatto.");
        }

        // procedi con l'update
        repo.update(index, p);
    }


    public void delete(int index) { repo.remove(index); }
}