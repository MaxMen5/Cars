package ru.bikchuraev.server.dao.interfaces;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.bikchuraev.api.entity.IEntity;

import java.util.List;

@Log4j
public abstract class AbstractDao<E extends IEntity> implements IDao<E> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected List<E> query(String sql) {
        log.info(sql);
        return jdbcTemplate.query(sql, rowMapper());
    }

    public <E> List<E> query(String sql, RowMapper<E> fullRowMapper) {
        log.info(sql);
        return jdbcTemplate.query(sql, fullRowMapper);
    }

    protected <T> T queryForObject(String sql, Class<T> requiredType) {
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, requiredType);
    }

    protected void update(String sql) {
        log.info(sql);
        jdbcTemplate.update(sql);
    }

}
