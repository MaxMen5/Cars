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

    List<FullMaker> loadAllMakers(MakerFilter filter);

    List<Country> loadAllCountries();

    List<FullCar> loadAllCars();

    void saveMaker(MakerEdit makerEdit);

    List<FullCar> loadMakerCars(Integer authorId);

    List<FullCar> loadNotAllCars(Integer authorId);

    void updateMaker(Integer authorId, MakerEdit changedAuthor);

    void deleteMakerById(Integer authorId);

    void deleteMakerCars(Integer authorId);

    List<SmallMaker> loadSmallMakers();

    List<Body> loadAllBody();

    List<FullCar> loadAllCars(CarFilter carFilter);

    void saveCar(CarEdit carEdit);

    void updateCar(Integer bookId, CarEdit changedBook);

    void deleteCarById(Integer bookId);

    boolean login(String login, String password);

    void logout();
}
