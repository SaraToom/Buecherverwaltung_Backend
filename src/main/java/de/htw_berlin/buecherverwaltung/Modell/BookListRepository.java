package de.htw_berlin.buecherverwaltung.Modell;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookListRepository extends CrudRepository<BookList, Long> {
    Optional<BookList> findByName(String name);
}
