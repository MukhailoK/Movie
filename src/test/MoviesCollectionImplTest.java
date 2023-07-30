package test;

import dao.MoviesCollectionImpl;
import model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MoviesCollectionImplTest {
    private MoviesCollectionImpl moviesCollection;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        moviesCollection = new MoviesCollectionImpl();

        // Common test data setup
        movie1 = new Movie(1L, "Movie 1", "Director 1", "Action", now);
        movie2 = new Movie(2L, "Movie 2", "Director 2", "Drama", now.plusDays(2));
        movie3 = new Movie(3L, "Movie 3", "Director 3", "Comedy", now.plusDays(5));

        moviesCollection.addMovie(movie1);
        moviesCollection.addMovie(movie2);
        moviesCollection.addMovie(movie3);
    }

    @Test
    void testAddMovie() {
        Movie movie = new Movie(4L, "Movie 4", "Director 4", "Sci-Fi", now);

        boolean added = moviesCollection.addMovie(movie);
        assertTrue(added);
        assertEquals(4, moviesCollection.totalQuantity());
    }

    @Test
    void testAddNullMovie() {
        boolean added = moviesCollection.addMovie(null);
        assertFalse(added);
        assertEquals(3, moviesCollection.totalQuantity());
    }

    @Test
    void testAddDuplicateMovie() {
        Movie duplicateMovie = new Movie(1L, "Movie 1", "Director 1", "Action",now);

        boolean added = moviesCollection.addMovie(duplicateMovie);

        assertFalse(added);
        assertEquals(3, moviesCollection.totalQuantity());
    }

    @Test
    void testFindByIdExistingMovie() {
        long imdbId = 2L;
        Movie foundMovie = moviesCollection.findById(imdbId);
        assertNotNull(foundMovie);
        assertEquals(movie2, foundMovie);
    }

    @Test
    void testFindByIdNonexistentMovie() {
        long imdbId = 4L;
        Movie foundMovie = moviesCollection.findById(imdbId);
        assertNull(foundMovie);
    }

    @Test
    void testRemoveMovie() {
        Movie removedMovie = moviesCollection.removeMovie(2L);

        assertEquals(movie2, removedMovie);
        assertEquals(2, moviesCollection.totalQuantity());
    }

    @Test
    void testRemoveNonexistentMovie() {
        Movie removedMovie = moviesCollection.removeMovie(4L);

        Assertions.assertNull(removedMovie);
        assertEquals(3, moviesCollection.totalQuantity());
    }

    @Test
    void testFindByGenre() {
        Iterable<Movie> foundMovies = moviesCollection.findByGenre("Action");

        Iterator<Movie> iterator = foundMovies.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(movie1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByDirector() {
        Iterable<Movie> foundMovies = moviesCollection.findByDirector("Director 3");

        Iterator<Movie> iterator = foundMovies.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(movie3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindMoviesCreatedBetweenDates() {
        LocalDate fromDate = LocalDate.of(2023, 7, 31);
        LocalDate toDate = LocalDate.of(2023, 8, 4);

        Iterable<Movie> foundMovies = moviesCollection.findMoviesCreatedBetweenDates(fromDate, toDate);

        Iterator<Movie> iterator = foundMovies.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(movie2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testTotalQuantity() {
        assertEquals(3, moviesCollection.totalQuantity());
    }

}





