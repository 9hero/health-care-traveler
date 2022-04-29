package com.healthtrip.travelcare.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
public class DataSourceConfig {

    private String url ="jdbc:postgresql://"+System.getenv("DBurl")+":5432/postgres";

    private String username = System.getenv("DBusername");

    private String password = System.getenv("DBpassword");

    private String driverClassName = "org.postgresql.Driver";

    @Bean
    public DataSource datasource() {
        System.out.println(System.getenv("DBusername")+"why is it null");
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }
}
