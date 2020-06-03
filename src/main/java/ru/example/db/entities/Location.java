package ru.example.db.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Location implements CommonEntity {
    private Long id;
    private String name;
}
