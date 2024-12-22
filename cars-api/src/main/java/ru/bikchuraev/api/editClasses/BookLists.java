package ru.bikchuraev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bikchuraev.api.entity.Genre;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookLists implements Serializable {
    private List<SmallAuthor> authors;
    private List<Genre> genres;
}
