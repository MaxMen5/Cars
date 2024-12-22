package ru.bikchuraev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bikchuraev.api.entity.Genre;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookEdit implements Serializable {
    private String name;
    private SmallAuthor author;
    private Integer year;
    private Genre genre;
    private Integer pages;
}
