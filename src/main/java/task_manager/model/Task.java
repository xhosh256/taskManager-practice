package task_manager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tasks")
public class Task {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Size(max = 32, message = "Name of your task must be up to 32 characters long.")
    @NotBlank(message = "Name of your task cannot be blank.")
    private String name;

    @Size(max = 200, message = "Description of your task must be up to 200 characters long.")
    @NotBlank(message = "Description of your task cannot be blank.")
    private String description;
}
