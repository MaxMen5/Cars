package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.bikchuraev.api.editClasses.BookEdit;
import ru.bikchuraev.api.editClasses.BookFilter;
import ru.bikchuraev.api.editClasses.FullBook;
import ru.bikchuraev.api.entity.Book;

import java.util.List;

public interface IBookDao extends IDao<Book> {

    @Override
    default RowMapper<Book> rowMapper() {
        return (resultSet, i) -> {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthorId(resultSet.getInt("author_country_id"));
            book.setYear(resultSet.getInt("birthday_year"));
            book.setGenreId(resultSet.getInt("book_genre_id"));
            book.setPageCount(resultSet.getInt("page_count"));
            return book;
        };
    }

    //================================================================================================================//

    @Transactional
    List<FullBook> findAll(BookFilter filter);

    @Transactional
    List<FullBook> findAll();

    @Transactional
    List<FullBook> findNotAllBooks(Integer id);

    @Transactional
    void deleteBookById(Integer id);

    @Transactional
    void saveBook(BookEdit book);

    @Transactional
    void update(Integer Id, BookEdit book);

    @Transactional
    List<FullBook> findAuthorBooks(Integer id);

    @Transactional
    void deleteAuthorBooks(Integer id);

    default RowMapper<FullBook> fullRowMapper() {
        return (resultSet, i) -> {
            FullBook book = new FullBook();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthorId(resultSet.getInt("author_country_id"));
            book.setAuthorName(resultSet.getString("author_name"));
            book.setYear(resultSet.getInt("birthday_year"));
            book.setGenreId(resultSet.getInt("book_genre_id"));
            book.setGenreName(resultSet.getString("book_genre_name"));
            book.setPageCount(resultSet.getInt("page_count"));
            return book;
        };
    }
}
