package ru.example.db.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class CountCargoesInItemResponse {
    private String locationName;
    private Integer locationId;
    private String loadsName;
    private Integer loadsId;
}
