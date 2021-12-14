package ru.r5am;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {
        // Здесь можно исключить ненужные автоконфигурации для ускорения запуска
        // Все автоконфигурации можно посмотреть в org.springframework.boot.autoconfigure
        DataSourceAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
        H2ConsoleAutoConfiguration.class
})
public class ApiTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiTestApplication.class, args);
    }
}
