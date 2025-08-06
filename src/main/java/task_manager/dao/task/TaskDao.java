package task_manager.dao.task;

import org.springframework.data.repository.CrudRepository;
import task_manager.model.Task;

import java.util.List;

public interface TaskDao extends CrudRepository<Task, Long> {
    List<Task> findAllByUserId(Long user_id);
}
