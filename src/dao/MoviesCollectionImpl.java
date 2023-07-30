package dao;

import model.Movie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class MoviesCollectionImpl implements MovesCollection {
    private List<Movie> movies;

    public MoviesCollectionImpl() {
        movies = new ArrayList<>();
    }

    @Override
    public boolean addMovie(Movie movie) {
        if (movie == null) {
            return false;
        }
        int index = Collections.binarySearch(movies, movie);
        if (index >= 0) {
            return false;
        }
        index = -index - 1;
        movies.add(index, movie);
        return true;
    }

    @Override
    public Movie removeMovie(long imdb) {
        Movie movie = findById(imdb);
        movies.remove(movie);
        return movie;
    }

    @Override
    public Movie findById(long imdb) {
        Movie pattern = new Movie(imdb, null, null, null, null);
        int index = Collections.binarySearch(movies, pattern);
        return index < 0 ? null : movies.get(index);
    }

    private Iterable<Movie> findByProperty(Predicate<Movie> propertyPredicate) {
        List<Movie> matchingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (propertyPredicate.test(movie)) {
                matchingMovies.add(movie);
            }
        }
        return matchingMovies;
    }


    @Override
    public Iterable<Movie> findByGenre(String genre) {
        Predicate<Movie> genrePredicate = movie -> genre.equals(movie.getGenre());
        return findByProperty(genrePredicate);
    }


    @Override
    public Iterable<Movie> findByDirector(String director) {
        Predicate<Movie> directorPredicate = movie -> director.equals(movie.getDirector());
        return findByProperty(directorPredicate);
    }


    @Override
    public Iterable<Movie> findMoviesCreatedBetweenDates(LocalDate from, LocalDate to) {
        Predicate<Movie> datePredicate = movie -> {
            LocalDate date = movie.getDate();
            return date.isAfter(from) && date.isBefore(to);
        };
        return findByProperty(datePredicate);
    }

    @Override
    public int totalQuantity() {
        return movies.size();
    }

    @Override
    public Iterator<Movie> iterator() {
        return new Iterator<>() {
            int pos;

            @Override
            public boolean hasNext() {
                return pos < movies.size();
            }

            @Override
            public Movie next() {
                return movies.get(pos++);
            }
        };
    }

}
