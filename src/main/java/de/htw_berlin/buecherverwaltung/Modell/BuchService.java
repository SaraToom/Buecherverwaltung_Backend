package de.htw_berlin.buecherverwaltung.Modell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class BuchService {
    @Autowired
    private BuchRepository repository;

    // Alle Bücher aus der Datenbank holen
    public List<Buch> getAllBooks() {
        List<Buch> buecher = new ArrayList<>();
        repository.findAll().forEach(buecher::add);
        return buecher;
    }

    // Ein neues Buch in der Datenbank speichern
    public Buch saveBook(Buch buch) {
        return repository.save(buch);
    }
}
