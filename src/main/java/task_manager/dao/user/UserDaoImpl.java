package task_manager.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import task_manager.model.User;

import java.util.Arrays;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(User user) {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "insert into users default values returning id"
        );

        factory.setReturnGeneratedKeys(true);

        PreparedStatementCreator stmt = factory.newPreparedStatementCreator(
                Arrays.asList()
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(stmt, keyHolder);

        Long user_id = keyHolder.getKey().longValue();
        user.setId(user_id);
    }
}
