package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerFilter;
import ru.bikchuraev.api.editClasses.FullMaker;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Maker;

import java.util.List;

public interface IMakerDao extends IDao<Maker> {

    @Override
    default RowMapper<Maker> rowMapper() {
        return (resultSet, i) -> {
            Maker maker = new Maker();
            maker.setId(resultSet.getInt("id"));
            maker.setName(resultSet.getString("name"));
            maker.setCountryId(resultSet.getInt("maker_country_id"));
            maker.setBirthYear(resultSet.getInt("birthday_year"));
            maker.setBookList(resultSet.getString("car_list"));
            return maker;
        };
    }

    //================================================================================================================//

    @Transactional
    List<FullMaker> findAll(MakerFilter filter);

    @Transactional
    List<SmallMaker> findSmallMakers();

    @Transactional
    void deleteMakerById(Integer id);

    @Transactional
    void saveMaker(MakerEdit maker);

    @Transactional
    void update(Integer Id, MakerEdit maker);

    default RowMapper<SmallMaker> smallRowMapper() {
        return (resultSet, i) -> {
            SmallMaker maker = new SmallMaker();
            maker.setId(resultSet.getInt("id"));
            maker.setName(resultSet.getString("name"));
            return maker;
        };
    }

    default RowMapper<FullMaker> fullRowMapper() {
        return (resultSet, i) -> {
            FullMaker maker = new FullMaker();
            maker.setId(resultSet.getInt("id"));
            maker.setName(resultSet.getString("name"));
            maker.setCountryId(resultSet.getInt("maker_country_id"));
            maker.setCountryName(resultSet.getString("country_name"));
            maker.setBirthYear(resultSet.getInt("birthday_year"));
            maker.setBookList(resultSet.getString("book_list"));
            return maker;
        };
    }
}
