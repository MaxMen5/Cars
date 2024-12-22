package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.entity.Body;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.IBodyDao;

import java.util.List;

@Component
@Lazy
public class PgBodyDao extends AbstractDao<Body> implements IBodyDao {

    @Override
    public List<Body> findAll() {
        return query("select * from body order by id");
    }
}
