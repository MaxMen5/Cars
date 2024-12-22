package ru.bikchuraev.server.controller;

import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.AuthorEdit;
import ru.bikchuraev.api.editClasses.AuthorFilter;
import ru.bikchuraev.api.editClasses.BookEdit;
import ru.bikchuraev.api.editClasses.BookFilter;
import ru.bikchuraev.api.editClasses.FullAuthor;
import ru.bikchuraev.api.editClasses.FullBook;
import ru.bikchuraev.api.editClasses.SmallAuthor;
import ru.bikchuraev.api.entity.Country;
import ru.bikchuraev.api.entity.Genre;
import ru.bikchuraev.api.servcie.LibraryServerService;
import ru.bikchuraev.server.dao.interfaces.IAuthorDao;
import ru.bikchuraev.server.dao.interfaces.IBookDao;
import ru.bikchuraev.server.dao.interfaces.ICountryDao;
import ru.bikchuraev.server.dao.interfaces.IGenreDao;
import ru.bikchuraev.server.service.AuthManager;

import java.util.List;

@Component
public class LibraryServerServiceImpl implements LibraryServerService {

    private final IAuthorDao authorDao;
    private final IBookDao bookDao;
    private final ICountryDao countryDao;
    private final IGenreDao genreDao;
    private final AuthManager authManager;

    public LibraryServerServiceImpl(IAuthorDao authorDao, IBookDao bookDao, ICountryDao countryDao, IGenreDao genreDao, AuthManager authManager) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.countryDao = countryDao;
        this.genreDao = genreDao;
        this.authManager = authManager;
    }

    @Override
    public boolean isLoggedIn() {
        return authManager.isLoggedIn();
    }

    @Override
    public List<FullAuthor> loadAllAuthors(AuthorFilter filter) {
        return authorDao.findAll(filter);
    }

    @Override
    public List<Country> loadAllCountries() {
        return countryDao.findAll();
    }

    @Override
    public List<FullBook> loadAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public void saveAuthor(AuthorEdit authorEdit) {
        authorDao.saveAuthor(authorEdit);
    }

    @Override
    public List<FullBook> loadAuthorBooks(Integer authorId) {
        return bookDao.findAuthorBooks(authorId);
    }

    @Override
    public List<FullBook> loadNotAllBooks(Integer authorId) {
        return bookDao.findNotAllBooks(authorId);
    }

    @Override
    public void updateAuthor(Integer authorId, AuthorEdit changedAuthor) {
        authorDao.update(authorId, changedAuthor);
    }

    @Override
    public void deleteAuthorById(Integer authorId) {
        authorDao.deleteAuthorById(authorId);
    }

    @Override
    public void deleteAuthorBooks(Integer authorId) {
        bookDao.deleteAuthorBooks(authorId);
    }

    @Override
    public List<SmallAuthor> loadSmallAuthors() {
        return authorDao.findSmallAuthors();
    }

    @Override
    public List<Genre> loadAllGenres() {
        return genreDao.findAll();
    }

    @Override
    public List<FullBook> loadAllBooks(BookFilter bookFilter) {
        return bookDao.findAll(bookFilter);
    }

    @Override
    public void saveBook(BookEdit bookEdit) {
        bookDao.saveBook(bookEdit);
    }

    @Override
    public void updateBook(Integer bookId, BookEdit changedBook) {
        bookDao.update(bookId, changedBook);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        bookDao.deleteBookById(bookId);
    }

    @Override
    public boolean login(String login, String password) {
        return authManager.login(login, password);
    }

    @Override
    public void logout() {
        authManager.logout();
    }
}
