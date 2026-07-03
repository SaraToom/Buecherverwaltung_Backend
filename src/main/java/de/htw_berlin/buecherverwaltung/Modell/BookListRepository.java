package de.htw_berlin.buecherverwaltung.Modell;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookListRepository extends CrudRepository<BookList, Long> {
    List<BookList> findByUser(User user);
}
