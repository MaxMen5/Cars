package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarFilter;
import ru.bikchuraev.api.editClasses.FullCar;
import ru.bikchuraev.api.entity.Car;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.ICarDao;

import java.util.List;

import static ru.bikchuraev.server.utils.ServerUtils.isBlank;

@Component
@Lazy
public class PgCarDao extends AbstractDao<Car> implements ICarDao {

    @Override
    public List<FullCar> findAll(CarFilter filter) {
        return query("select " +
                "c.id as id, " +
                "c.name as name, " +
                "c.car_maker_id as car_maker_id, " +
                "m.name as maker_name, " +
                "c.year as birthday_year, " +
                "c.car_body_id as car_body_id, " +
                "b.name as body_name, " +
                "c.mile as mile " +
                "from car c " +
                "inner join maker m on c.car_maker_id = m.id " +
                "inner join body b on c.car_body_id = b.id " +
                "where 1=1 " +
                (isBlank(filter.getName()) ? "" : "and c.name like '%" + filter.getName() + "%' ") +
                (isBlank(filter.getMaker()) ? "" : "and m.name like '%" + filter.getMaker() + "%' ") +
                (isBlank(filter.getYear()) ? "" : "and c.year = " + filter.getYear() + " ") +
                (isBlank(filter.getBody()) ? "" : "and b.name like '%" + filter.getBody() + "%' ") +
                (isBlank(filter.getMile()) ? "" : "and c.mile = " + filter.getMile() + " ") +
                "order by c.id", fullRowMapper());
    }

    @Override
    public List<FullCar> findAll() {
        return query("select " +
                "c.id as id, " +
                "c.name as name, " +
                "c.car_maker_id as car_maker_id, " +
                "m.name as maker_name, " +
                "c.year as birthday_year, " +
                "c.car_body_id as car_body_id, " +
                "b.name as body_name, " +
                "c.mile as mile " +
                "from car c " +
                "inner join maker m on c.car_maker_id = m.id " +
                "inner join body b on c.car_body_id = b.id " +
                "order by c.id", fullRowMapper());
    }
// TODO дальше не менял
    @Override
    public List<FullCar> findNotAllCars(Integer id) {
        return query("select " +
                "c.id as id, " +
                "c.name as name, " +
                "c.car_maker_id as car_maker_id, " +
                "m.name as maker_name, " +
                "c.year as birthday_year, " +
                "c.car_body_id as car_body_id, " +
                "b.name as body_name, " +
                "c.mile as mile " +
                "from car c " +
                "inner join maker m on c.car_maker_id = m.id " +
                "inner join body b on c.car_body_id = b.id " +
                "where с.car_maker_id <> " + id +
                "order by с.id", fullRowMapper());
    }

    @Override
    public void deleteCarById(Integer id) {
        update("delete from car where id = " + id);
    }

    @Override
    public void saveCar(CarEdit car) {
        update("insert into car (name, car_maker_id, year, car_body_id, mile) values ('" +
                car.getName() + "', '" +
                car.getMaker().getId() + "', " +
                car.getYear() + ", '" +
                car.getBody().getId() + "', " +
                car.getMile() + ");");
    }

    @Override
    public void update(Integer id, CarEdit car) {
        update("update car set name = '" + car.getName() + "', " +
                "car_maker_id = " + car.getMaker().getId() +
                ", year = " + car.getYear() +
                ", car_body_id = " + car.getBody().getId() +
                ", mile = " + car.getMile() +
                " where id = " + id);
    }
//
    @Override
    public List<FullCar> findMakerCars(Integer id) {
        return query("select " +
                "c.id as id, " +
                "c.name as name, " +
                "c.car_maker_id as car_maker_id, " +
                "m.name as maker_name, " +
                "c.year as birthday_year, " +
                "c.car_body_id as car_body_id, " +
                "b.name as body_name, " +
                "c.mile as mile " +
                "from car c " +
                "inner join maker m on c.car_maker_id = m.id " +
                "inner join body b on c.car_body_id = b.id " +
                "where с.car_maker_id = " + id +
                "order by с.id", fullRowMapper());
    }

    @Override
    public void deleteMakerCars(Integer id) {
        update("delete from car where car_maker_id = " + id);
    }
}
