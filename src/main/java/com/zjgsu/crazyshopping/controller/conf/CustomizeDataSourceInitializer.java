package com.zjgsu.crazyshopping.controller.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class CustomizeDataSourceInitializer {
    @Value("classpath:data/create.sql")
    private Resource sqlScriptSchema;
    @Value("${spring.datasource.url}") private String datasourceUrl;


    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        return dataSourceInitializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        if (!isDatabaseFileExists()) {
            resourceDatabasePopulator.addScript(sqlScriptSchema);
            resourceDatabasePopulator.setSeparator(";");
        }

        return resourceDatabasePopulator;
    }
    private boolean isDatabaseFileExists() {
        File databaseFile = new File(datasourceUrl.replace("jdbc:sqlite:", ""));
        return databaseFile.exists();
    }

}

