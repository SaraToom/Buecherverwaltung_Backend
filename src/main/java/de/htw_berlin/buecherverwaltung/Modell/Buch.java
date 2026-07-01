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
    private int releaseYear;       // NEU
    private int stars;            
    private String review;         
    private boolean isFavorite;    
    private String status;         
    

   
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

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}