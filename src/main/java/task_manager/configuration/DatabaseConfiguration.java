package task_manager.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Data
public class DatabaseConfiguration {
    DatabaseConfigurationProperties databaseConfigurationProperties;

    @Autowired
    public DatabaseConfiguration(DatabaseConfigurationProperties databaseConfigurationProperties) {
        this.databaseConfigurationProperties = databaseConfigurationProperties;
    }

    @Bean("dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(databaseConfigurationProperties.getDriverName());
        dataSource.setJdbcUrl(databaseConfigurationProperties.getUrl());
        dataSource.setUsername(databaseConfigurationProperties.getUsername());
        dataSource.setPassword(databaseConfigurationProperties.getPassword());

        return dataSource;
    }
}
