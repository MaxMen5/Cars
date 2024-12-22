package ru.bikchuraev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bikchuraev.api.entity.Body;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CarLists implements Serializable {
    private List<SmallMaker> makers;
    private List<Body> bodies;
}
