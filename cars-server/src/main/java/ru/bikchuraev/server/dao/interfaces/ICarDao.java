package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarFilter;
import ru.bikchuraev.api.editClasses.FullCar;
import ru.bikchuraev.api.entity.Car;

import java.util.List;

public interface ICarDao extends IDao<Car> {

    @Override
    default RowMapper<Car> rowMapper() {
        return (resultSet, i) -> {
            Car car = new Car();
            car.setId(resultSet.getInt("id"));
            car.setName(resultSet.getString("name"));
            car.setAuthorId(resultSet.getInt("car_maker_id"));
            car.setYear(resultSet.getInt("year"));
            car.setGenreId(resultSet.getInt("car_body_id"));
            car.setPageCount(resultSet.getInt("mile"));
            return car;
        };
    }

    //================================================================================================================//

    @Transactional
    List<FullCar> findAll(CarFilter filter);

    @Transactional
    List<FullCar> findAll();

    @Transactional
    List<FullCar> findNotAllCars(Integer id);

    @Transactional
    void deleteCarById(Integer id);

    @Transactional
    void saveCar(CarEdit car);

    @Transactional
    void update(Integer Id, CarEdit car);

    @Transactional
    List<FullCar> findMakerCars(Integer id);

    @Transactional
    void deleteMakerCars(Integer id);

    default RowMapper<FullCar> fullRowMapper() {
        return (resultSet, i) -> {
            FullCar car = new FullCar();
            car.setId(resultSet.getInt("id"));
            car.setName(resultSet.getString("name"));
            car.setAuthorId(resultSet.getInt("car_maker_id"));
            car.setAuthorName(resultSet.getString("maker_name"));
            car.setYear(resultSet.getInt("year"));
            car.setGenreId(resultSet.getInt("car_body_id"));
            car.setGenreName(resultSet.getString("body_name"));
            car.setPageCount(resultSet.getInt("mile"));
            return car;
        };
    }
}
