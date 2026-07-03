package de.htw_berlin.buecherverwaltung.Modell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuchServiceTest {

    @Mock
    private BuchRepository repository;

    @InjectMocks
    private BuchService service;

    private Buch buch1;
    private Buch buch2;

    @BeforeEach
    void setUp() {
        buch1 = new Buch();
        buch1.setId(1L);
        buch1.setTitle("Der Prozess");
        buch1.setAuthor("Franz Kafka");

        buch2 = new Buch();
        buch2.setId(2L);
        buch2.setTitle("Faust");
        buch2.setAuthor("Johann Wolfgang von Goethe");
    }

    @Test
    void getAllBooksForUser_gibtAlleBuecherDesNutzersZurueck() {
        User user = new User();
        user.setId(1L);
        when(repository.findByUser(user)).thenReturn(Arrays.asList(buch1, buch2));

        List<Buch> result = service.getAllBooksForUser(user);

        assertThat(result).containsExactly(buch1, buch2);
    }

    @Test
    void getAllBooksForUser_gibtLeereListeZurueckWennKeineBuecherVorhanden() {
        User user = new User();
        user.setId(1L);
        when(repository.findByUser(user)).thenReturn(List.of());

        List<Buch> result = service.getAllBooksForUser(user);

        assertThat(result).isEmpty();
    }

    @Test
    void saveBook_speichertUndGibtGespeichertesBuchZurueck() {
        when(repository.save(buch1)).thenReturn(buch1);

        Buch result = service.saveBook(buch1);

        assertThat(result).isEqualTo(buch1);
        verify(repository, times(1)).save(buch1);
    }

    @Test
    void deleteBookById_ruftRepositoryMitKorrekterIdAuf() {
        service.deleteBookById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
