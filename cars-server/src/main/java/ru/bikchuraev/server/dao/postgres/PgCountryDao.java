package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.entity.Country;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.ICountryDao;

import java.util.List;

@Component
@Lazy
public class PgCountryDao extends AbstractDao<Country> implements ICountryDao {

    @Override
    public List<Country> findAll() {
        return query("select * from country order by id");
    }
}
