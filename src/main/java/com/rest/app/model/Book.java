package com.rest.app.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book_record")
@Data
@Builder
public class Book {
    public Book(Long bookId, String name, String summary, int rating) {
        this.bookId = bookId;
        this.name = name;
        this.summary = summary;
        this.rating = rating;
    }

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @NonNull
    private String name;

    @NonNull
    private String summary;

    private int rating;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
