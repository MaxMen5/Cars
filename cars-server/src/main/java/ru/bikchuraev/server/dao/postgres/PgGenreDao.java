package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.entity.Body;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.IGenreDao;

import java.util.List;

@Component
@Lazy
public class PgGenreDao extends AbstractDao<Body> implements IGenreDao {

    @Override
    public List<Body> findAll() {
        return query("select * from genre order by id");
    }
}
