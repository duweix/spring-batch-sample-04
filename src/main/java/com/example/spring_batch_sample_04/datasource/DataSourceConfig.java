package com.example.spring_batch_sample_04.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        // @formatter:off
        return new EmbeddedDatabaseBuilder()
                .setName("dataSource")
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("classpath:db-script/init-db.sql"
                        , "classpath:org/springframework/batch/core/schema-drop-h2.sql"
                        , "classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
        // @formatter:on
    }
}
