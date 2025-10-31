package it.turingrubrica.app.domain.validation;

import it.turingrubrica.app.domain.Persona;

public class PersonaValidator {
    public void validate(Persona p) throws ValidationException {
        if (p == null) throw new ValidationException("Persona nulla");
        if (p.getNome() == null || p.getNome().isBlank())
            throw new ValidationException("Il nome è obbligatorio.");
        if (p.getCognome() == null || p.getCognome().isBlank())
            throw new ValidationException("Il cognome è obbligatorio.");
        if (p.getTelefono() == null || p.getTelefono().isBlank())
            throw new ValidationException("Il telefono è obbligatorio.");
        if (p.getEta() < 0)
            throw new ValidationException("L'età deve essere ≥ 0.");
    }
}