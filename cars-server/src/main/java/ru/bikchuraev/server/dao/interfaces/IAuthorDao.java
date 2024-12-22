package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.editClasses.AuthorEdit;
import ru.bikchuraev.api.editClasses.AuthorFilter;
import ru.bikchuraev.api.editClasses.FullAuthor;
import ru.bikchuraev.api.editClasses.SmallAuthor;
import ru.bikchuraev.api.entity.Author;

import java.util.List;

public interface IAuthorDao extends IDao<Author> {

    @Override
    default RowMapper<Author> rowMapper() {
        return (resultSet, i) -> {
            Author author = new Author();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            author.setCountryId(resultSet.getInt("author_country_id"));
            author.setBirthYear(resultSet.getInt("birthday_year"));
            author.setBookList(resultSet.getString("book_list"));
            return author;
        };
    }

    //================================================================================================================//

    @Transactional
    List<FullAuthor> findAll(AuthorFilter filter);

    @Transactional
    List<SmallAuthor> findSmallAuthors();

    @Transactional
    void deleteAuthorById(Integer id);

    @Transactional
    void saveAuthor(AuthorEdit author);

    @Transactional
    void update(Integer Id, AuthorEdit author);

    default RowMapper<SmallAuthor> smallRowMapper() {
        return (resultSet, i) -> {
            SmallAuthor author = new SmallAuthor();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            return author;
        };
    }

    default RowMapper<FullAuthor> fullRowMapper() {
        return (resultSet, i) -> {
            FullAuthor author = new FullAuthor();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            author.setCountryId(resultSet.getInt("author_country_id"));
            author.setCountryName(resultSet.getString("author_country_name"));
            author.setBirthYear(resultSet.getInt("birthday_year"));
            author.setBookList(resultSet.getString("book_list"));
            return author;
        };
    }
}
