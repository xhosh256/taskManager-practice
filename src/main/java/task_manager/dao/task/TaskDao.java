package task_manager.dao.task;

import task_manager.model.Task;
import task_manager.model.User;

import java.util.List;

public interface TaskDao {

    void saveTask(Task task);
    void deleteTask(Long task_id, Long user_id);
    List<Task> findAll(Long user_id);
    Task findById(Long id);
}
