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
public class CarFilter implements Serializable {
    private String name;
    private String author;
    private String year;
    private String genre;
    private String page;
}
