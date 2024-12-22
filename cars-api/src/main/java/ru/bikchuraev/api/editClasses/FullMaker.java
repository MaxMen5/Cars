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
public class FullMaker implements Serializable {
    private Integer id;
    private String name;
    private Integer countryId;
    private String countryName;
    private Integer birthYear;
    private String bookList;
}
