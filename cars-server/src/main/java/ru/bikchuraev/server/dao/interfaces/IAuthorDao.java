package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerFilter;
import ru.bikchuraev.api.editClasses.FullMaker;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Maker;

import java.util.List;

public interface IAuthorDao extends IDao<Maker> {

    @Override
    default RowMapper<Maker> rowMapper() {
        return (resultSet, i) -> {
            Maker maker = new Maker();
            maker.setId(resultSet.getInt("id"));
            maker.setName(resultSet.getString("name"));
            maker.setCountryId(resultSet.getInt("author_country_id"));
            maker.setBirthYear(resultSet.getInt("birthday_year"));
            maker.setBookList(resultSet.getString("book_list"));
            return maker;
        };
    }

    //================================================================================================================//

    @Transactional
    List<FullMaker> findAll(MakerFilter filter);

    @Transactional
    List<SmallMaker> findSmallAuthors();

    @Transactional
    void deleteAuthorById(Integer id);

    @Transactional
    void saveAuthor(MakerEdit author);

    @Transactional
    void update(Integer Id, MakerEdit author);

    default RowMapper<SmallMaker> smallRowMapper() {
        return (resultSet, i) -> {
            SmallMaker author = new SmallMaker();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            return author;
        };
    }

    default RowMapper<FullMaker> fullRowMapper() {
        return (resultSet, i) -> {
            FullMaker author = new FullMaker();
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
