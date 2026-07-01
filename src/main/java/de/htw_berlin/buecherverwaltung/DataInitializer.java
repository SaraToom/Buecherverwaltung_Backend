package de.htw_berlin.buecherverwaltung;

import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookListRepository listRepository;

    @Override
    public void run(String... args) throws Exception {
        if (listRepository.count() == 0) {
            List<String> defaultLists = Arrays.asList("Möchte ich lesen", "Gelesen", "Aktuell");
            for (String name : defaultLists) {
                listRepository.save(new BookList(name));
            }
            System.out.println("Default book lists initialized: " + defaultLists);
        }
    }
}
