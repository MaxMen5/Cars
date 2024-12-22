package ru.bikchuraev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FullBook implements Serializable {
    private Integer id;
    private String name;
    private Integer authorId;
    private String authorName;
    private Integer year;
    private Integer genreId;
    private String genreName;
    private Integer pageCount;
}
