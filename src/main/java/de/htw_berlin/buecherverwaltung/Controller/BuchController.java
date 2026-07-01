package de.htw_berlin.buecherverwaltung.Controller; 

import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/books")
public class BuchController {

    @Autowired
    private BuchService service;

    @GetMapping
    public List<Buch> getBooks() { 
        return service.getAllBooks();
    }

    @PostMapping
    public Buch createBook(@RequestBody Buch buch) {
        return service.saveBook(buch);
    }

    // NEU: Buch löschen per ID
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBookById(id); // Stelle sicher, dass dein BuchService diese Methode hat!
    }

    // NEU: Buch aktualisieren (Bearbeiten / Favorit / Status wechseln)
    @PutMapping("/{id}")
    public Buch updateBook(@PathVariable Long id, @RequestBody Buch updatedBuch) {
        // Hier rufen wir deinen Service auf, um das bestehende Buch zu überschreiben
        updatedBuch.setId(id); // Stelle sicher, dass die ID gesetzt ist
        return service.saveBook(updatedBuch); 
    }
}