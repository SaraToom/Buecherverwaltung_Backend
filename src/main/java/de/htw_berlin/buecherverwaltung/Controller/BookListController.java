package de.htw_berlin.buecherverwaltung.Controller;

import de.htw_berlin.buecherverwaltung.Auth.UserService;
import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchRepository;
import de.htw_berlin.buecherverwaltung.Modell.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class BookListController {

    @Autowired
    private BookListRepository listRepository;

    @Autowired
    private BuchRepository bookRepository;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }

    @GetMapping
    public List<BookList> getAllLists() {
        return listRepository.findByUser(getCurrentUser());
    }

    @PostMapping
    public BookList createList(@RequestBody BookList list) {
        list.setUser(getCurrentUser());
        return listRepository.save(list);
    }

    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Long id) {
        List<Buch> books = bookRepository.findByBookListId(id);
        for (Buch book : books) {
            book.setBookList(null);
            bookRepository.save(book);
        }
        listRepository.deleteById(id);
    }
}
