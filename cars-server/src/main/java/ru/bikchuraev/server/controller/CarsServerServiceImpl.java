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
import ru.bikchuraev.server.dao.interfaces.IMakerDao;
import ru.bikchuraev.server.dao.interfaces.ICarDao;
import ru.bikchuraev.server.dao.interfaces.ICountryDao;
import ru.bikchuraev.server.dao.interfaces.IBodyDao;
import ru.bikchuraev.server.service.AuthManager;

import java.util.List;

@Component
public class CarsServerServiceImpl implements CarsServerService {

    private final IMakerDao makerDao;
    private final ICarDao carDao;
    private final ICountryDao countryDao;
    private final IBodyDao bodyDao;
    private final AuthManager authManager;

    public CarsServerServiceImpl(IMakerDao makerDao, ICarDao carDao, ICountryDao countryDao, IBodyDao bodyDao, AuthManager authManager) {
        this.makerDao = makerDao;
        this.carDao = carDao;
        this.countryDao = countryDao;
        this.bodyDao = bodyDao;
        this.authManager = authManager;
    }

    @Override
    public boolean isLoggedIn() {
        return authManager.isLoggedIn();
    }

    @Override
    public List<FullMaker> loadAllMakers(MakerFilter filter) {
        return makerDao.findAll(filter);
    }

    @Override
    public List<Country> loadAllCountries() {
        return countryDao.findAll();
    }

    @Override
    public List<FullCar> loadAllCars() {
        return carDao.findAll();
    }

    @Override
    public void saveMaker(MakerEdit makerEdit) {
        makerDao.saveMaker(makerEdit);
    }

    @Override
    public List<FullCar> loadMakerCars(Integer authorId) {
        return carDao.findMakerCars(authorId);
    }

    @Override
    public List<FullCar> loadNotAllCars(Integer authorId) {
        return carDao.findNotAllCars(authorId);
    }

    @Override
    public void updateMaker(Integer authorId, MakerEdit changedAuthor) {
        makerDao.update(authorId, changedAuthor);
    }

    @Override
    public void deleteMakerById(Integer authorId) {
        makerDao.deleteMakerById(authorId);
    }

    @Override
    public void deleteMakerCars(Integer authorId) {
        carDao.deleteMakerCars(authorId);
    }

    @Override
    public List<SmallMaker> loadSmallMakers() {
        return makerDao.findSmallMakers();
    }

    @Override
    public List<Body> loadAllBody() {
        return bodyDao.findAll();
    }

    @Override
    public List<FullCar> loadAllCars(CarFilter carFilter) {
        return carDao.findAll(carFilter);
    }

    @Override
    public void saveCar(CarEdit carEdit) {
        carDao.saveCar(carEdit);
    }

    @Override
    public void updateCar(Integer bookId, CarEdit changedBook) {
        carDao.update(bookId, changedBook);
    }

    @Override
    public void deleteCarById(Integer bookId) {
        carDao.deleteCarById(bookId);
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
