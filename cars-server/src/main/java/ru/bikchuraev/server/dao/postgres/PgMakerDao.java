package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerFilter;
import ru.bikchuraev.api.editClasses.FullMaker;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Maker;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.IMakerDao;

import java.util.List;

import static ru.bikchuraev.server.utils.ServerUtils.isBlank;

@Component
@Lazy
public class PgMakerDao extends AbstractDao<Maker> implements IMakerDao {

    @Override
    public List<FullMaker> findAll(MakerFilter filter) {
        return query("select " +
                "m.id as id, " +
                "m.name as name, " +
                "m.maker_country_id as maker_country_id, " +
                "con.name as country_name, " +
                "m.birthday_year as birthday_year, " +
                "array_to_string(array(select c.name from car c where c.car_maker_id = m.id order by c.name), ', ')  as car_list " +
                "from maker m " +
                "inner join country con on m.maker_country_id = con.id " +
                "where 1=1 " +
                (isBlank(filter.getName()) ? "" : "and m.name like '%" + filter.getName() + "%' ") +
                (isBlank(filter.getCountry()) ? "" : "and con.name like '%" + filter.getCountry() + "%' ") +
                (isBlank(filter.getYear()) ? "" : "and m.birthday_year = " + filter.getYear() + " ") +
                "order by m.id", fullRowMapper());
    }

    @Override
    public List<SmallMaker> findSmallMakers() {
        return query("select id, name from maker order by id", smallRowMapper());
    }

    @Override
    public void deleteMakerById(Integer id) {
        update("delete from maker where id = " + id);
    }

    @Override
    public void saveMaker(MakerEdit maker) {
        update("insert into maker (name, maker_country_id, birthday_year) values ('" +
                maker.getName() + "', " +
                maker.getCountry().getId() + ", " +
                maker.getYear() + ");");
    }

    @Override
    public void update(Integer id, MakerEdit maker) {
        update("update maker set name = '" + maker.getName() + "', " +
                "maker_country_id = " + maker.getCountry().getId() +
                ", birthday_year = " + maker.getYear() +
                " where id = " + id);
    }
}
