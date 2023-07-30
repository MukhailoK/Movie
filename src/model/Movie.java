package model;

import java.time.LocalDate;
import java.util.Objects;

public class Movie implements Comparable<Movie> {
    private long imdb;
    private String title;
    private String director;
    private String genre;
    private LocalDate date;

    public Movie(long imdb, String title, String director, String genre, LocalDate date) {
        this.imdb = imdb;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return imdb == movie.imdb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdb);
    }

    public long getImdb() {
        return imdb;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(Movie o) {
        return Long.compare(imdb, o.getImdb());
    }
}
