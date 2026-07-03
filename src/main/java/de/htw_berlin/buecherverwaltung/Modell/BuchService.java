package de.htw_berlin.buecherverwaltung.Modell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class BuchService {

    @Autowired
    private BuchRepository repository;

    public List<Buch> getAllBooksForUser(User user) {
        return repository.findByUser(user);
    }

    public Buch saveBook(Buch buch) {
        return repository.save(buch);
    }

    public void deleteBookById(Long id) {
        repository.deleteById(id);
    }
}
