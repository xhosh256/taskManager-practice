package task_manager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Task {
    private Long id;
    private Long user_id;

    @Size(max = 32, message = "Name of your task must be up to 32 characters long.")
    @NotBlank(message = "Name of your task cannot be blank.")
    private String name;

    @Size(max = 200, message = "Description of your task must be up to 200 characters long.")
    @NotBlank(message = "Description of your task cannot be blank.")
    private String description;
}
