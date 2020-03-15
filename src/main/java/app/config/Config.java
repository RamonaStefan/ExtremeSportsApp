package app.config;

import app.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlDataSource;
import app.dao.Dao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class Config {
    @Bean
    public DataSource dataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/extremesports");
        dataSource.setUser("root");
        dataSource.setPassword("stefanramona17");

        return dataSource;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public Dao userDao(DataSource dataSource) throws SQLException {
        return new Dao(dataSource);
    }

    @Bean
    public Service userService(Dao dao, ObjectMapper objectMapper){
        return new Service(dao, objectMapper);
    }
}
