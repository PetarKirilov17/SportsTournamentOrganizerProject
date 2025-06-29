package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

// import jakarta.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    // Commenting out custom DataSource configuration to let Spring Boot auto-configure
    // Spring Boot will automatically configure the datasource from application.properties
    /*
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("username"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }
    */
}
