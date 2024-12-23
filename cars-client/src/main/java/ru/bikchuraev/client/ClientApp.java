package ru.bikchuraev.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ru.bikchuraev.api.servcie.CarsServerService;
import ru.bikchuraev.client.gui.MainFrame;

@Configuration
@ComponentScan
@PropertySource("classpath:/config/client.properties")
public class ClientApp {

    @Value("#{ environment['server.address'] }")
    private String serverAddress;

    @Bean
    public CarsServerService carsServerService() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl(serverAddress);
        rmiProxyFactoryBean.setServiceInterface(CarsServerService.class);
        rmiProxyFactoryBean.afterPropertiesSet();
        return (CarsServerService) rmiProxyFactoryBean.getObject();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ClientApp.class);
        ctx.getBean(MainFrame.class);
    }
}
