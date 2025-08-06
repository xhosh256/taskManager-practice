package task_manager.dao.user;

import org.springframework.data.repository.CrudRepository;
import task_manager.model.Task;
import task_manager.model.User;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
}
