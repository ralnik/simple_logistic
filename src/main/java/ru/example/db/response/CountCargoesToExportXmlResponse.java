package ru.example.db.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.db.entities.Loads;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountCargoesToExportXmlResponse implements CommonXMLRoot {
    private String locationName;
    private Long locationId;
    private List<Loads> loads;

}
