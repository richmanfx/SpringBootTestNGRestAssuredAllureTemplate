package ru.r5am.entities;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import ru.r5am.utils.YamlProcessing;

/**
 * Тестовый стенд
 */
@Slf4j
@Component
public class Stand {

    @Setter @Getter public String name;
    @Setter @Getter public String url;
    @Setter @Getter public String port;

    @Autowired private YamlProcessing yamlProcessing;

    public Stand(){}

    public Stand(String name, String url, String port) {
        this.name = name;
        this.url = url;
        this.port = port;
    }

    public void get(String standName, String standFileName) throws IOException {

        // Все стенды
        List<Stand> stands = yamlProcessing.getEntitiesFromFile(standFileName, Stand.class);

        // Только нужный стенд
        Stand currentStand = (Stand) stands.stream()
                .filter(stand -> stand.name.equals(standName)).distinct().toArray()[0];

        // Заполнить все поля нужного стенда
        this.name = standName;
        this.url = currentStand.getUrl();
        this.port = currentStand.getPort();
    }

    @Override
    public String toString() {
        return String.format("Current stand: name => %s, URL => %s, TCP port => %s", name, url, port);
    }

}
