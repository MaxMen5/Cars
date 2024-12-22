package ru.bikchuraev.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ru.bikchuraev.api.servcie.LibraryServerService;
import ru.bikchuraev.client.gui.MainFrame;

@Configuration
@ComponentScan
@PropertySource("classpath:/config/client.properties")
public class ClientApp {

    @Value("#{ environment['server.address'] }")
    private String serverAddress;

    @Bean
    public LibraryServerService libraryServerService() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl(serverAddress);
        rmiProxyFactoryBean.setServiceInterface(LibraryServerService.class);
        rmiProxyFactoryBean.afterPropertiesSet();
        return (LibraryServerService) rmiProxyFactoryBean.getObject();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ClientApp.class);
        ctx.getBean(MainFrame.class);
    }
}
