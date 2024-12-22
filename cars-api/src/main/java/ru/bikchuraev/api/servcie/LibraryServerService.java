package ru.bikchuraev.api.servcie;

import ru.bikchuraev.api.editClasses.AuthorEdit;
import ru.bikchuraev.api.editClasses.AuthorFilter;
import ru.bikchuraev.api.editClasses.BookEdit;
import ru.bikchuraev.api.editClasses.BookFilter;
import ru.bikchuraev.api.editClasses.FullAuthor;
import ru.bikchuraev.api.editClasses.FullBook;
import ru.bikchuraev.api.editClasses.SmallAuthor;
import ru.bikchuraev.api.entity.Country;
import ru.bikchuraev.api.entity.Genre;

import java.util.List;

public interface LibraryServerService {

    boolean isLoggedIn();

    List<FullAuthor> loadAllAuthors(AuthorFilter filter);

    List<Country> loadAllCountries();

    List<FullBook> loadAllBooks();

    void saveAuthor(AuthorEdit authorEdit);

    List<FullBook> loadAuthorBooks(Integer authorId);

    List<FullBook> loadNotAllBooks(Integer authorId);

    void updateAuthor(Integer authorId, AuthorEdit changedAuthor);

    void deleteAuthorById(Integer authorId);

    void deleteAuthorBooks(Integer authorId);

    List<SmallAuthor> loadSmallAuthors();

    List<Genre> loadAllGenres();

    List<FullBook> loadAllBooks(BookFilter bookFilter);

    void saveBook(BookEdit bookEdit);

    void updateBook(Integer bookId, BookEdit changedBook);

    void deleteBookById(Integer bookId);

    boolean login(String login, String password);

    void logout();
}
