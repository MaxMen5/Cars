package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.AuthorEdit;
import ru.bikchuraev.api.editClasses.AuthorFilter;
import ru.bikchuraev.api.editClasses.FullAuthor;
import ru.bikchuraev.api.editClasses.SmallAuthor;
import ru.bikchuraev.api.entity.Author;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.IAuthorDao;

import java.util.List;

import static ru.bikchuraev.server.utils.ServerUtils.isBlank;

@Component
@Lazy
public class PgAuthorDao extends AbstractDao<Author> implements IAuthorDao {

    @Override
    public List<FullAuthor> findAll(AuthorFilter filter) {
        return query("select " +
                "a.id as id, " +
                "a.name as name, " +
                "a.author_country_id as author_country_id, " +
                "c.name as author_country_name, " +
                "a.birthday_year as birthday_year, " +
                "array_to_string(array(select b.name from book b where b.book_author_id = a.id order by b.name), ', ')  as book_list " +
                "from author a " +
                "inner join country c on a.author_country_id = c.id " +
                "where 1=1 " +
                (isBlank(filter.getName()) ? "" : "and a.name like '%" + filter.getName() + "%' ") +
                (isBlank(filter.getCountry()) ? "" : "and c.name like '%" + filter.getCountry() + "%' ") +
                (isBlank(filter.getYear()) ? "" : "and a.birthday_year = " + filter.getYear() + " ") +
                "order by a.id", fullRowMapper());
    }

    @Override
    public List<SmallAuthor> findSmallAuthors() {
        return query("select id, name from author order by id", smallRowMapper());
    }

    @Override
    public void deleteAuthorById(Integer id) {
        update("delete from author where id = " + id);
    }

    @Override
    public void saveAuthor(AuthorEdit author) {
        update("insert into author (name, author_country_id, birthday_year) values ('" +
                author.getName() + "', " +
                author.getCountry().getId() + ", " +
                author.getYear() + ");");
    }

    @Override
    public void update(Integer id, AuthorEdit author) {
        update("update author set name = '" + author.getName() + "', " +
                "author_country_id = " + author.getCountry().getId() +
                ", birthday_year = " + author.getYear() +
                " where id = " + id);
    }
}
