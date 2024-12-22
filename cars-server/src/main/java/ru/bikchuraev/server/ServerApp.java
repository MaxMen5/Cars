package ru.bikchuraev.server;

import lombok.extern.log4j.Log4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bikchuraev.api.servcie.LibraryServerService;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:/config/server.properties")
@Log4j
public class ServerApp {

    @Value("#{ environment['jdbc.url'] }")
    private String databaseUrl;

    @Value("#{ environment['jdbc.username'] }")
    private String databaseUserName = "";

    @Value("#{ environment['jdbc.password'] }")
    private String databasePassword = "";

    @Value("#{ environment['database.driverClassName'] }")
    private String driverClassName;

    @Value("#{ environment['jdbc.validation.query'] }")
    private String databaseValidationQuery;

    @Value("#{ environment['port'] }")
    private int serverPort;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUserName);
        dataSource.setPassword(databasePassword);
        dataSource.setValidationQuery(databaseValidationQuery);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public RmiServiceExporter libraryServerServiceExporter(LibraryServerService libraryServerService) {
        RmiServiceExporter serviceExporter = new RmiServiceExporter();

        serviceExporter.setServiceName("library");
        serviceExporter.setService(libraryServerService);
        serviceExporter.setServiceInterface(LibraryServerService.class);
        serviceExporter.setRegistryPort(serverPort);

        return serviceExporter;
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(ServerApp.class);
    }
}
