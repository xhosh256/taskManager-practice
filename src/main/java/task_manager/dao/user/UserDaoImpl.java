package task_manager.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import task_manager.model.User;

import javax.sql.DataSource;
import java.sql.*;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    DataSource dataSource;

    @Autowired
    public UserDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveUser(User user) {

        String sql = "insert into users default values";

        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.executeUpdate();

            try(ResultSet keys = stmt.getGeneratedKeys()) {
                if(keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
