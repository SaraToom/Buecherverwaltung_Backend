package de.htw_berlin.buecherverwaltung.Modell; 

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "books") 
public class Buch { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private Integer releaseYear;       // NEU
    private Integer stars;            
    private String review;         
    
    @JsonProperty("isFavorite")
    private Boolean isFavorite;    
    private String status;

    // Legacy-Spalte aus alter DB-Version – wird nicht mehr im Frontend genutzt
    @JsonIgnore
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;         
    
    @ManyToOne
    @JoinColumn(name = "list_id")
    private BookList bookList;

   
    public Buch() {} 

    // Getter & Setter für alle Felder
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public Boolean getIsFavorite() { return isFavorite; }
    public void setIsFavorite(Boolean isFavorite) { this.isFavorite = isFavorite; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BookList getBookList() { return bookList; }
    public void setBookList(BookList bookList) { this.bookList = bookList; }
}