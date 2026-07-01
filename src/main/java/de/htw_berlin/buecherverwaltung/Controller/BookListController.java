package de.htw_berlin.buecherverwaltung.Controller;

import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/lists")
public class BookListController {

    @Autowired
    private BookListRepository listRepository;

    @Autowired
    private BuchRepository bookRepository;

    @GetMapping
    public List<BookList> getAllLists() {
        List<BookList> lists = new ArrayList<>();
        listRepository.findAll().forEach(lists::add);
        return lists;
    }

    @PostMapping
    public BookList createList(@RequestBody BookList list) {
        return listRepository.save(list);
    }

    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Long id) {
        // Disassociate books before deleting the list
        List<Buch> books = bookRepository.findByBookListId(id);
        for (Buch book : books) {
            book.setBookList(null);
            bookRepository.save(book);
        }
        listRepository.deleteById(id);
    }
}
