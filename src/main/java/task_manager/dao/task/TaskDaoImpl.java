package task_manager.dao.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import task_manager.model.Task;
import task_manager.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("taskDaoImpl")
public class TaskDaoImpl implements TaskDao {

    DataSource dataSource;

    @Autowired
    public TaskDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveTask(Task task) {
        String sql = "insert into tasks (user_id, name, description) values (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, task.getUser_id());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());

            stmt.executeUpdate();

            try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                if(resultSet.next()) {
                    task.setId(resultSet.getLong(1));
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll(Long user_id) {
        String sql = "select * from tasks where user_id = ?";
        List<Task> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, user_id);
            stmt.executeQuery();

            try (ResultSet resultSet = stmt.getResultSet()) {
                while (resultSet.next()) {
                    Task task = new Task();
                    task.setId(resultSet.getLong(1));
                    task.setUser_id(resultSet.getLong(2));
                    task.setName(resultSet.getString(3));
                    task.setDescription(resultSet.getString(4));
                    result.add(task);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    @Override
    public void deleteTask(Long task_id, Long user_id) {
        String sql = "delete from tasks where id = ? and user_id = ?";

        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, task_id);
            stmt.setLong(2, user_id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Task findById(Long id) {
        String sql = "select * from tasks where id = ?";
        Task task = new Task();

        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, id);
        try (ResultSet resultSet = stmt.executeQuery()) {
            if(resultSet.next()) {
                task.setId(resultSet.getLong(1));
                task.setUser_id(resultSet.getLong(2));
                task.setName(resultSet.getString(3));
                task.setDescription(resultSet.getString(4));
                return task;
            }
        }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
