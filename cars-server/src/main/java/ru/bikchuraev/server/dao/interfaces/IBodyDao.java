package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.entity.Body;

import java.util.List;

public interface IBodyDao extends IDao<Body> {

    @Override
    default RowMapper<Body> rowMapper() {
        return (resultSet, i) -> {
            Body body = new Body();
            body.setId(resultSet.getInt("id"));
            body.setName(resultSet.getString("name"));
            return body;
        };
    }

    //================================================================================================================//

    @Transactional
    List<Body> findAll();
}
