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
 * Клиент
 */
@Slf4j
@Component
public class Client {

    @Setter @Getter public String lastname;
    @Setter @Getter public String firstname;
    @Setter @Getter public String patronymic;

    @Autowired private YamlProcessing yamlProcessing;

    public Client(){}

    public Client(String lastname, String firstname, String patronymic) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.patronymic = patronymic;
    }

    public void get(String clientLastname, String clientsFileName) throws IOException {

        // Все клиенты
        List<Client> clients = yamlProcessing.getEntitiesFromFile(clientsFileName, Client.class);

        // Только нужный клиент
        Client currentClient = (Client) clients.stream()
                .filter(client -> client.lastname.equals(clientLastname)).distinct().toArray()[0];

        // Заполнить все поля нужного клиента
        this.lastname = clientLastname;
        this.firstname = currentClient.getFirstname();
        this.patronymic = currentClient.getPatronymic();
    }

}
