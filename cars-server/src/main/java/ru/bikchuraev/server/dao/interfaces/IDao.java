package ru.bikchuraev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.bikchuraev.api.entity.IEntity;

public interface IDao<E extends IEntity> {
    RowMapper<E> rowMapper();
}
