package de.htw_berlin.buecherverwaltung.Modell; 

import jakarta.persistence.*;

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
    private Boolean isFavorite;    
    private String status;         
    
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

    public Boolean isFavorite() { return isFavorite; }
    public void setFavorite(Boolean isFavorite) { this.isFavorite = isFavorite; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BookList getBookList() { return bookList; }
    public void setBookList(BookList bookList) { this.bookList = bookList; }
}