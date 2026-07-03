package de.htw_berlin.buecherverwaltung.Controller;

import de.htw_berlin.buecherverwaltung.Auth.UserService;
import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchService;
import de.htw_berlin.buecherverwaltung.Modell.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BuchController {

    @Autowired
    private BuchService service;

    @Autowired
    private BookListRepository bookListRepository;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }

    @GetMapping
    public List<Buch> getBooks() {
        return service.getAllBooksForUser(getCurrentUser());
    }

    @PostMapping
    public Buch createBook(@RequestBody Buch buch) {
        buch.setUser(getCurrentUser());
        resolveBookList(buch);
        return service.saveBook(buch);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBookById(id);
    }

    @PutMapping("/{id}")
    public Buch updateBook(@PathVariable Long id, @RequestBody Buch updatedBuch) {
        updatedBuch.setId(id);
        updatedBuch.setUser(getCurrentUser());
        resolveBookList(updatedBuch);
        return service.saveBook(updatedBuch);
    }

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
