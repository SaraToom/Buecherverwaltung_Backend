package de.htw_berlin.buecherverwaltung.Controller; 

import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BuchController {

    @Autowired
    private BuchService service;

    @PostMapping("/books")
    public Buch createBook(@RequestBody Buch buch) {
        return service.saveBook(buch);
    }

    @GetMapping("/books")
    public List<Buch> getBooks() { 
        return service.getAllBooks();
    }
}