package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.entity.Country;

import java.util.List;

public interface ICountryDao extends IDao<Country> {

    @Override
    default RowMapper<Country> rowMapper() {
        return (resultSet, i) -> {
            Country country = new Country();
            country.setId(resultSet.getInt("id"));
            country.setName(resultSet.getString("name"));
            return country;
        };
    }

    //================================================================================================================//

    @Transactional
    List<Country> findAll();
}
