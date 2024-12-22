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
public class AuthorFilter implements Serializable {
    private String name;
    private String country;
    private String year;
}
