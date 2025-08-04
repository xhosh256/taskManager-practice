package task_manager.model;

import lombok.Data;

@Data
public class Task {
    private Long id;
    private Long user_id;
    private String name;
    private String description;
}
