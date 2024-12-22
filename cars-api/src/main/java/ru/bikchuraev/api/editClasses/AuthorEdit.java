package ru.bikchuraev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bikchuraev.api.entity.Country;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthorEdit implements Serializable {
    private String name;
    private Country country;
    private Integer year;
    private List<FullBook> book;
}