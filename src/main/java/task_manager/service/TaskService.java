package task_manager.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import task_manager.dao.task.TaskDao;
import task_manager.model.Task;
import task_manager.model.User;

import java.util.List;


@Service
@Data
public class TaskService {

    TaskDao taskDao;

    @Autowired
    public TaskService(@Qualifier("taskDaoImpl") TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void createTask(Task task) {
        taskDao.saveTask(task);
    }

    public List<Task> getAllTasks(Long user_id) {
        return taskDao.findAll(user_id);
    }

    public Task findById(Long id) {
        return taskDao.findById(id);
    }

    public void deleteTask(Long task_id, Long user_id) {
        taskDao.deleteTask(task_id, user_id);
    }
}
