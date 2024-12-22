package ru.bikchuraev.api.servcie;

import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerFilter;
import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarFilter;
import ru.bikchuraev.api.editClasses.FullMaker;
import ru.bikchuraev.api.editClasses.FullCar;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Country;
import ru.bikchuraev.api.entity.Body;

import java.util.List;

public interface CarsServerService {

    boolean isLoggedIn();

    List<FullMaker> loadAllAuthors(MakerFilter filter);

    List<Country> loadAllCountries();

    List<FullCar> loadAllBooks();

    void saveAuthor(MakerEdit makerEdit);

    List<FullCar> loadAuthorBooks(Integer authorId);

    List<FullCar> loadNotAllBooks(Integer authorId);

    void updateAuthor(Integer authorId, MakerEdit changedAuthor);

    void deleteAuthorById(Integer authorId);

    void deleteAuthorBooks(Integer authorId);

    List<SmallMaker> loadSmallAuthors();

    List<Body> loadAllGenres();

    List<FullCar> loadAllBooks(CarFilter carFilter);

    void saveBook(CarEdit carEdit);

    void updateBook(Integer bookId, CarEdit changedBook);

    void deleteBookById(Integer bookId);

    boolean login(String login, String password);

    void logout();
}
