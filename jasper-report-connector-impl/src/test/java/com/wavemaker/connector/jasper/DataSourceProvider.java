package com.wavemaker.connector.jasper;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 26/6/20
 */
@Service
public class DataSourceProvider {

    @Value("${db.url}")
    private String connectionURL;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver.class.name}")
    private String driverClass;

    private BasicDataSource dataSource = null;


    public DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            try {
                dataSource.setDriverClassName(driverClass);
                dataSource.setUrl(connectionURL);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
            } catch (Exception e) {
                throw new RuntimeException("Failed to build database connection", e);
            }
        }
        return dataSource;
    }

}
