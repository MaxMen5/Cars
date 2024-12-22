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
public class FullCar implements Serializable {
    private Integer id;
    private String name;
    private Integer makerId;
    private String makerName;
    private Integer year;
    private Integer bodyId;
    private String bodyName;
    private Integer mile;
}
