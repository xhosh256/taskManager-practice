package task_manager.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import task_manager.dao.task.TaskDao;
import task_manager.model.Task;

import java.util.List;


@Service
@Data
public class TaskService {

    TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void createTask(Task task) {
        taskDao.save(task);
    }

    public List<Task> getAllTasks(Long user_id) {
        return taskDao.findAllByUserId(user_id);
    }

    public void deleteTask(Long task_id, Long user_id) {
        taskDao.delete(taskDao.findById(task_id).orElse(null));
    }
}
