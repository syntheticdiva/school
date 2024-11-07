package school.project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SchoolCreateDTO {
    private String name;
    private String address;
}