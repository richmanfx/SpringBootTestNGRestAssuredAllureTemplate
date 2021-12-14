package ru.r5am.basetest;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import io.restassured.http.Cookies;
import org.testng.annotations.DataProvider;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework. beans. factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests ;

import ru.r5am.entities.*;
import ru.r5am.ApiTestApplication;

/**
 * Родительский класс всех API тестов
 */
@Slf4j
@SpringBootTest(classes = ApiTestApplication.class)
public class ApiBaseTest extends AbstractTestNGSpringContextTests {

    protected String standUri;

    protected String basicToken;    // Токен BASIC аутентификации
    protected Cookies cookies = new Cookies();

    protected RequestSpecification requestSpecification;
    @Value("${dataFiles.standsDataFilePath}")
    protected String standsDataFilePath;
    @Value("${dataFiles.clientsDataFilePath}")
    protected String clientsDataFilePath;
    @Autowired protected Stand stand;
    @Autowired protected Client client;
    @Autowired protected RestRequest restRequest;
    @Autowired protected AllureReport allureReport;
    @Autowired protected RestResponse restResponse;

    /**
     * Создать кодированный токен для Basic аутентификации
     * @param name Имя пользователя
     * @return password Пароль пользователя
     */
    protected void basicTokenMake(String name, String password) {
        basicToken = new String(Base64.getEncoder().encode(String.format("%s:%s", name, password).getBytes()));
        log.debug("basicToken: {}", basicToken);
    }

    /**
     * Data-Провайдер для параметризации валютой
     */
    @DataProvider()
    protected static Object[][] currencyDataProvider() {
        return new Object[][]{
                {"RUB"},
                {"USD"},
                {"EUR"}
        };
    }

    /**
     * Date-Провайдер для параметризации клиентом
     */
    @DataProvider()
    protected static Object[][] clientDataProvider() {
        return new Object[][]{
                {"Автотестюк"},
                {"Автоботов"}
        };
    }

}
