package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.entity.Genre;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.IGenreDao;

import java.util.List;

@Component
@Lazy
public class PgGenreDao extends AbstractDao<Genre> implements IGenreDao {

    @Override
    public List<Genre> findAll() {
        return query("select * from genre order by id");
    }
}
