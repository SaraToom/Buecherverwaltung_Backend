package de.htw_berlin.buecherverwaltung.Controller; 

import de.htw_berlin.buecherverwaltung.Modell.Buch; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BuchController {

    @GetMapping("/books")
    public List<Buch> getAlleBuecher() { 
        List<Buch> buecher = new ArrayList<>(); 
        
        // Testdaten
        buecher.add(new Buch("Harry Potter und der Stein der Weisen", "J.K. Rowling", "Fantasy", true));
        buecher.add(new Buch("11 Minuten", "Paulo Coelho", "Roman", false));
        buecher.add(new Buch("Stolz und Vorurteil", "Jane Austen", "Klassiker", true));
        buecher.add(new Buch("The Housemaid", "Freida McFadden", "Thriller", false));
        
        return buecher;
    }
}