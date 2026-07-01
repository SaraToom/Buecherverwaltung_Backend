package de.htw_berlin.buecherverwaltung.Controller; 

import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BuchController {

    @Autowired
    private BuchService service;

    @Autowired
    private BookListRepository bookListRepository;

    @GetMapping
    public List<Buch> getBooks() { 
        return service.getAllBooks();
    }

    @PostMapping
    public Buch createBook(@RequestBody Buch buch) {
        resolveBookList(buch);
        return service.saveBook(buch);
    }

    // Buch löschen per ID
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBookById(id);
    }

    // Buch aktualisieren (Bearbeiten / Favorit / Status wechseln)
    @PutMapping("/{id}")
    public Buch updateBook(@PathVariable Long id, @RequestBody Buch updatedBuch) {
        updatedBuch.setId(id);
        resolveBookList(updatedBuch);
        return service.saveBook(updatedBuch); 
    }

    /**
     * Löst die bookList-Referenz sicher auf:
     * Falls das Frontend nur { id: X } schickt, laden wir das echte Objekt aus der DB.
     * Falls die ID nicht existiert oder null ist, wird bookList auf null gesetzt.
     */
    private void resolveBookList(Buch buch) {
        if (buch.getBookList() != null && buch.getBookList().getId() != null) {
            bookListRepository.findById(buch.getBookList().getId())
                .ifPresentOrElse(
                    buch::setBookList,
                    () -> buch.setBookList(null)
                );
        } else {
            buch.setBookList(null);
        }
    }
}