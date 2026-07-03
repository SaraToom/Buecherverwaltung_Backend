package de.htw_berlin.buecherverwaltung.Controller;

import de.htw_berlin.buecherverwaltung.Modell.BookList;
import de.htw_berlin.buecherverwaltung.Modell.BookListRepository;
import de.htw_berlin.buecherverwaltung.Modell.Buch;
import de.htw_berlin.buecherverwaltung.Modell.BuchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuchController.class)
class BuchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BuchService service;

    @MockitoBean
    private BookListRepository bookListRepository;

    @Test
    void getBooks_gibtAlleBuecherAlsJsonZurueck() throws Exception {
        Buch buch = new Buch();
        buch.setId(1L);
        buch.setTitle("Der Prozess");
        buch.setAuthor("Franz Kafka");

        when(service.getAllBooks()).thenReturn(List.of(buch));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Der Prozess"))
                .andExpect(jsonPath("$[0].author").value("Franz Kafka"));
    }

    @Test
    void createBook_speichertBuchOhneBookListUndGibtEsZurueck() throws Exception {
        Buch neuesBuch = new Buch();
        neuesBuch.setTitle("Faust");
        neuesBuch.setAuthor("Johann Wolfgang von Goethe");

        Buch gespeichertesBuch = new Buch();
        gespeichertesBuch.setId(1L);
        gespeichertesBuch.setTitle("Faust");
        gespeichertesBuch.setAuthor("Johann Wolfgang von Goethe");

        when(service.saveBook(any(Buch.class))).thenReturn(gespeichertesBuch);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(neuesBuch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Faust"));
    }

    @Test
    void createBook_loestBookListReferenzUeberIdAuf() throws Exception {
        BookList list = new BookList("Aktuell");
        list.setId(5L);

        Buch neuesBuch = new Buch();
        neuesBuch.setTitle("1984");
        neuesBuch.setAuthor("George Orwell");
        BookList referenz = new BookList();
        referenz.setId(5L);
        neuesBuch.setBookList(referenz);

        when(bookListRepository.findById(5L)).thenReturn(Optional.of(list));
        when(service.saveBook(any(Buch.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(neuesBuch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookList.id").value(5))
                .andExpect(jsonPath("$.bookList.name").value("Aktuell"));

        verify(bookListRepository, times(1)).findById(5L);
    }

    @Test
    void createBook_setztBookListAufNullWennIdNichtExistiert() throws Exception {
        Buch neuesBuch = new Buch();
        neuesBuch.setTitle("1984");
        neuesBuch.setAuthor("George Orwell");
        BookList referenz = new BookList();
        referenz.setId(99L);
        neuesBuch.setBookList(referenz);

        when(bookListRepository.findById(99L)).thenReturn(Optional.empty());
        when(service.saveBook(any(Buch.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(neuesBuch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookList").doesNotExist());
    }

    @Test
    void deleteBook_ruftServiceMitKorrekterIdAuf() throws Exception {
        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteBookById(1L);
    }

    @Test
    void updateBook_aktualisiertBuchUndGibtEsZurueck() throws Exception {
        Buch updatedBuch = new Buch();
        updatedBuch.setTitle("Faust - Zweiter Teil");
        updatedBuch.setAuthor("Johann Wolfgang von Goethe");
        updatedBuch.setIsFavorite(true);

        when(service.saveBook(any(Buch.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBuch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Faust - Zweiter Teil"))
                .andExpect(jsonPath("$.isFavorite").value(true));
    }
}
