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
    private boolean isRead;

    public Buch() {} 

    public Buch(String title, String author, String genre, boolean isRead) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isRead = isRead;
    }

    // Getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isRead() { return isRead; }

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setRead(boolean read) { isRead = read; }
}