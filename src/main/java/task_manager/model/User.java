package task_manager.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {

    @NotNull
    private Long id;
}
