package task_manager.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import task_manager.dao.user.UserDao;
import task_manager.model.User;


@Service
@Data
@Slf4j
public class UserService {

    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser() {
        User user = new User();
        userDao.save(user);
        return user;
    }
}
