package task_manager.dao.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import task_manager.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository("taskDaoImpl")
@Slf4j
public class TaskDaoImpl implements TaskDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveTask(Task task) {
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "insert into tasks (user_id, name, description) values (?, ?, ?) " +
                        "returning id",
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR
        );
        factory.setReturnGeneratedKeys(true);

        PreparedStatementCreator stmt = factory.newPreparedStatementCreator(
                Arrays.asList(
                        task.getUser_id(),
                        task.getName(),
                        task.getDescription()
                )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(stmt, keyHolder);

        Long task_id = keyHolder.getKey().longValue();
        task.setId(task_id);
        log.info("task was created");
    }

    @Override
    public List<Task> findAll(Long user_id) {
        String sql = "select * from tasks where user_id = ?";
        List<Task> result = new ArrayList<>();

        return jdbcTemplate.query(
                "select * from tasks where user_id = ?",
                this::mapRowToTask,
                user_id
        );
    }

    private Task mapRowToTask(ResultSet resultSet, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getLong(1));
        task.setUser_id(resultSet.getLong(2));
        task.setName(resultSet.getString(3));
        task.setDescription(resultSet.getString(4));

        return task;
    }

    @Override
    public void deleteTask(Long task_id, Long user_id) {
        jdbcTemplate.update(
                "delete from tasks where id = ? and user_id = ?",
                task_id, user_id
        );
    }
}
