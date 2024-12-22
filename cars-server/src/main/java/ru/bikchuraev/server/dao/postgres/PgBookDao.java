package ru.bikchuraev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.BookEdit;
import ru.bikchuraev.api.editClasses.BookFilter;
import ru.bikchuraev.api.editClasses.FullBook;
import ru.bikchuraev.api.entity.Book;
import ru.bikchuraev.server.dao.interfaces.AbstractDao;
import ru.bikchuraev.server.dao.interfaces.IBookDao;

import java.util.List;

import static ru.bikchuraev.server.utils.ServerUtils.isBlank;

@Component
@Lazy
public class PgBookDao extends AbstractDao<Book> implements IBookDao {

    @Override
    public List<FullBook> findAll(BookFilter filter) {
        return query("select " +
                "b.id as id, " +
                "b.name as name, " +
                "b.book_author_id as author_country_id, " +
                "a.name as author_name, " +
                "b.year as birthday_year, " +
                "b.book_genre_id as book_genre_id, " +
                "g.name as book_genre_name, " +
                "b.page_count as page_count " +
                "from book b " +
                "inner join author a on b.book_author_id = a.id " +
                "inner join genre g on b.book_genre_id = g.id " +
                "where 1=1 " +
                (isBlank(filter.getName()) ? "" : "and b.name like '%" + filter.getName() + "%' ") +
                (isBlank(filter.getAuthor()) ? "" : "and a.name like '%" + filter.getAuthor() + "%' ") +
                (isBlank(filter.getYear()) ? "" : "and b.year = " + filter.getYear() + " ") +
                (isBlank(filter.getGenre()) ? "" : "and g.name like '%" + filter.getGenre() + "%' ") +
                (isBlank(filter.getPage()) ? "" : "and b.page_count = " + filter.getPage() + " ") +
                "order by b.id", fullRowMapper());
    }

    @Override
    public List<FullBook> findAll() {
        return query("select " +
                "b.id as id, " +
                "b.name as name, " +
                "b.book_author_id as author_country_id, " +
                "a.name as author_name, " +
                "b.year as birthday_year, " +
                "b.book_genre_id as book_genre_id, " +
                "g.name as book_genre_name, " +
                "b.page_count as page_count " +
                "from book b " +
                "inner join author a on b.book_author_id = a.id " +
                "inner join genre g on b.book_genre_id = g.id " +
                "order by b.id", fullRowMapper());
    }

    @Override
    public List<FullBook> findNotAllBooks(Integer id) {
        return query("select " +
                "b.id as id, " +
                "b.name as name, " +
                "b.book_author_id as author_country_id, " +
                "a.name as author_name, " +
                "b.year as birthday_year, " +
                "b.book_genre_id as book_genre_id, " +
                "g.name as book_genre_name, " +
                "b.page_count as page_count " +
                "from book b " +
                "inner join author a on b.book_author_id = a.id " +
                "inner join genre g on b.book_genre_id = g.id " +
                "where b.book_author_id <> " + id +
                "order by b.id", fullRowMapper());
    }

    @Override
    public void deleteBookById(Integer id) {
        update("delete from book where id = " + id);
    }

    @Override
    public void saveBook(BookEdit book) {
        update("insert into book (name, book_author_id, year, book_genre_id, page_count) values ('" +
                book.getName() + "', '" +
                book.getAuthor().getId() + "', " +
                book.getYear() + ", '" +
                book.getGenre().getId() + "', " +
                book.getPages() + ");");
    }

    @Override
    public void update(Integer id, BookEdit book) {
        update("update book set name = '" + book.getName() + "', " +
                "book_author_id = " + book.getAuthor().getId() +
                ", year = " + book.getYear() +
                ", book_genre_id = " + book.getGenre().getId() +
                ", page_count = " + book.getPages() +
                " where id = " + id);
    }

    @Override
    public List<FullBook> findAuthorBooks(Integer id) {
        return query("select " +
                "b.id as id, " +
                "b.name as name, " +
                "b.book_author_id as author_country_id, " +
                "a.name as author_name, " +
                "b.year as birthday_year, " +
                "b.book_genre_id as book_genre_id, " +
                "g.name as book_genre_name, " +
                "b.page_count as page_count " +
                "from book b " +
                "inner join author a on b.book_author_id = a.id " +
                "inner join genre g on b.book_genre_id = g.id " +
                "where b.book_author_id = " + id +
                "order by b.id", fullRowMapper());
    }

    @Override
    public void deleteAuthorBooks(Integer id) {
        update("delete from book where book_author_id = " + id);
    }
}
