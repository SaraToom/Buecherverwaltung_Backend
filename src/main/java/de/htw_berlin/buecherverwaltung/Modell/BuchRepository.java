package de.htw_berlin.buecherverwaltung.Modell; 

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuchRepository extends CrudRepository<Buch, Long> {
}
