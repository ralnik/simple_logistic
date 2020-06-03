package ru.example.db.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Loads implements CommonEntity {
    private Long id;
    private String name;
    private Location location;
}
