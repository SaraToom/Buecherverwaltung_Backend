package de.htw_berlin.buecherverwaltung.Modell; 

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BuchRepository extends CrudRepository<Buch, Long> {
    List<Buch> findByBookListId(Long listId);
    List<Buch> findByUser(User user);
}
