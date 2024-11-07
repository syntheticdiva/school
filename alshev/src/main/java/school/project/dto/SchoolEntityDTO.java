package school.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchoolEntityDTO {
    private Long id;

    @NotBlank(message = "Название школы обязательно для заполнения")
    @Size(min = 2, max = 100, message = "Название школы должно содержать от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Адрес школы обязателен для заполнения")
    @Size(max = 200, message = "Адрес школы не должен превышать 200 символов")
    private String address;
}
