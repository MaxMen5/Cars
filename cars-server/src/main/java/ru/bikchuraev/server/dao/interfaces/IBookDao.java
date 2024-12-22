package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarFilter;
import ru.bikchuraev.api.editClasses.FullCar;
import ru.bikchuraev.api.entity.Car;

import java.util.List;

public interface IBookDao extends IDao<Car> {

    @Override
    default RowMapper<Car> rowMapper() {
        return (resultSet, i) -> {
            Car car = new Car();
            car.setId(resultSet.getInt("id"));
            car.setName(resultSet.getString("name"));
            car.setAuthorId(resultSet.getInt("author_country_id"));
            car.setYear(resultSet.getInt("birthday_year"));
            car.setGenreId(resultSet.getInt("book_genre_id"));
            car.setPageCount(resultSet.getInt("page_count"));
            return car;
        };
    }

    //================================================================================================================//

    @Transactional
    List<FullCar> findAll(CarFilter filter);

    @Transactional
    List<FullCar> findAll();

    @Transactional
    List<FullCar> findNotAllBooks(Integer id);

    @Transactional
    void deleteBookById(Integer id);

    @Transactional
    void saveBook(CarEdit book);

    @Transactional
    void update(Integer Id, CarEdit book);

    @Transactional
    List<FullCar> findAuthorBooks(Integer id);

    @Transactional
    void deleteAuthorBooks(Integer id);

    default RowMapper<FullCar> fullRowMapper() {
        return (resultSet, i) -> {
            FullCar book = new FullCar();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthorId(resultSet.getInt("author_country_id"));
            book.setAuthorName(resultSet.getString("author_name"));
            book.setYear(resultSet.getInt("birthday_year"));
            book.setGenreId(resultSet.getInt("book_genre_id"));
            book.setGenreName(resultSet.getString("book_genre_name"));
            book.setPageCount(resultSet.getInt("page_count"));
            return book;
        };
    }
}
