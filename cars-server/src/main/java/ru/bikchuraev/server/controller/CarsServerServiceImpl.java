package ru.bikchuraev.server.controller;

import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerFilter;
import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarFilter;
import ru.bikchuraev.api.editClasses.FullMaker;
import ru.bikchuraev.api.editClasses.FullCar;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Country;
import ru.bikchuraev.api.entity.Body;
import ru.bikchuraev.api.servcie.CarsServerService;
import ru.bikchuraev.server.dao.interfaces.IAuthorDao;
import ru.bikchuraev.server.dao.interfaces.IBookDao;
import ru.bikchuraev.server.dao.interfaces.ICountryDao;
import ru.bikchuraev.server.dao.interfaces.IGenreDao;
import ru.bikchuraev.server.service.AuthManager;

import java.util.List;

@Component
public class CarsServerServiceImpl implements CarsServerService {

    private final IAuthorDao authorDao;
    private final IBookDao bookDao;
    private final ICountryDao countryDao;
    private final IGenreDao genreDao;
    private final AuthManager authManager;

    public CarsServerServiceImpl(IAuthorDao authorDao, IBookDao bookDao, ICountryDao countryDao, IGenreDao genreDao, AuthManager authManager) {
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
    public List<FullMaker> loadAllAuthors(MakerFilter filter) {
        return authorDao.findAll(filter);
    }

    @Override
    public List<Country> loadAllCountries() {
        return countryDao.findAll();
    }

    @Override
    public List<FullCar> loadAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public void saveAuthor(MakerEdit makerEdit) {
        authorDao.saveAuthor(makerEdit);
    }

    @Override
    public List<FullCar> loadAuthorBooks(Integer authorId) {
        return bookDao.findAuthorBooks(authorId);
    }

    @Override
    public List<FullCar> loadNotAllBooks(Integer authorId) {
        return bookDao.findNotAllBooks(authorId);
    }

    @Override
    public void updateAuthor(Integer authorId, MakerEdit changedAuthor) {
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
    public List<SmallMaker> loadSmallAuthors() {
        return authorDao.findSmallAuthors();
    }

    @Override
    public List<Body> loadAllGenres() {
        return genreDao.findAll();
    }

    @Override
    public List<FullCar> loadAllBooks(CarFilter carFilter) {
        return bookDao.findAll(carFilter);
    }

    @Override
    public void saveBook(CarEdit carEdit) {
        bookDao.saveBook(carEdit);
    }

    @Override
    public void updateBook(Integer bookId, CarEdit changedBook) {
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
