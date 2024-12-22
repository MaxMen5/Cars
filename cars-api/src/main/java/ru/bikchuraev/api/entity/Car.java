package ru.bikchuraev.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Car implements IEntity, Serializable {
    private Integer id;
    private String name;
    private Integer makerId;
    private Integer year;
    private Integer bodyId;
    private Integer mile;
}
