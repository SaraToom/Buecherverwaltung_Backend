package de.htw_berlin.buecherverwaltung;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Default-Regale werden jetzt beim Registrieren eines neuen Benutzers angelegt (UserService).
@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {}
}
