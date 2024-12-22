package ru.bikchuraev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bikchuraev.api.entity.Body;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CarEdit implements Serializable {
    private String name;
    private SmallMaker author;
    private Integer year;
    private Body body;
    private Integer pages;
}
