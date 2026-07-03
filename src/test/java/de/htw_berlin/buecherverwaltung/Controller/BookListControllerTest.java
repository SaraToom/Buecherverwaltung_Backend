package de.htw_berlin.buecherverwaltung.Controller;

import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookListController.class)
class BookListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookListRepository listRepository;

    @MockitoBean
    private BuchRepository bookRepository;

    @Test
    void getAllLists_gibtAlleListenZurueck() throws Exception {
        BookList list = new BookList("Aktuell");
        list.setId(1L);

        when(listRepository.findAll()).thenReturn(List.of(list));

        mockMvc.perform(get("/lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Aktuell"));
    }

    @Test
    void createList_speichertNeueListe() throws Exception {
        BookList neueListe = new BookList("Gelesen");
        BookList gespeicherteListe = new BookList("Gelesen");
        gespeicherteListe.setId(2L);

        when(listRepository.save(any(BookList.class))).thenReturn(gespeicherteListe);

        mockMvc.perform(post("/lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(neueListe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Gelesen"));
    }

    @Test
    void deleteList_entkoppeltZugehoerigeBuecherUndLoeschtDieListe() throws Exception {
        Buch buch = new Buch();
        buch.setId(10L);
        BookList list = new BookList("Aktuell");
        list.setId(1L);
        buch.setBookList(list);

        when(bookRepository.findByBookListId(1L)).thenReturn(List.of(buch));

        mockMvc.perform(delete("/lists/{id}", 1L))
                .andExpect(status().isOk());

        verify(bookRepository, times(1)).save(argThatBookListIsNull());
        verify(listRepository, times(1)).deleteById(1L);
    }

    private Buch argThatBookListIsNull() {
        return org.mockito.ArgumentMatchers.argThat(b -> b.getBookList() == null);
    }
}
