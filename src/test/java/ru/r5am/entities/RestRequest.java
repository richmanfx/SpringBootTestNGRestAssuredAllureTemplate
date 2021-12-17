package ru.r5am.entities;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import java.io. IOException;
import lombok.extern.slf4j.Slf4j;
import io.restassured.http.Cookies;
import java.net .URISyntaxException;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org. springframework.stereotype.Component;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans. factory. annotation.Autowired;

import ru.r5am.utils.JsonProcessing;

/**
 * REST запрос
 */
@Slf4j
@Component
public class RestRequest {
    @Setter @Getter String baseUri;
    @Setter @Getter String basePath;
    @Setter @Getter Cookies cookies;
    @Setter @Getter String userToken;
    @Autowired private JsonProcessing jsonProcessing;

    /**
     * Создать спецификацию на основе кук, базового URI и базового пути
     *
     * @param cookies  Куки
     * @param baseUri  Базовый URI
     * @param basePath Базовый путь
     * @return Спецификация запроса
     */
    public RequestSpecification requestSpecificationMake(Cookies cookies, String baseUri, String basePath) {
        this.baseUri = baseUri;
        this.basePath = basePath;
        this.cookies = cookies;
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setAccept("*/*")
                .setContentType("application/json; charset=utf-8")
                .addCookies(cookies)
                .build()
                .relaxedHTTPSValidation();
    }

    /**
     * Создать спецификацию запроса с токеном
     *
     * @param cookies    Куки
     * @param baseUri    Базовый URI
     * @param basePath   Базовый путь
     * @param basicToken Токен для BASIC авторизации
     * @return Спецификация запроса
     */
    public RequestSpecification requestSpecificationMake(
            Cookies cookies, String baseUri, String basePath, String basicToken) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setAccept("*/*")
                .setContentType("application/json; charset=utf-8")
                .addHeader("Authorization", String.format("Basic %s", basicToken))
                .addCookies(cookies)
                .build()
                .relaxedHTTPSValidation();
    }

    /**
     * Создать спецификацию запроса с множеством хидеров из словаря
     *
     * @param cookies  Куки
     * @param baseUri  Базовый URI
     * @param basePath Базовый путь
     * @param headers  Словарь заголовков
     * @return Спецификация запроса
     */
    public RequestSpecification requestSpecificationMake(
            Cookies cookies, String baseUri, String basePath, Map<String, String> headers) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setAccept("*/*")
                .setContentType("application/json; charset=utf-8")
                .addHeaders(headers)
                .addCookies(cookies)
                .build()
                .relaxedHTTPSValidation();
    }

    /**
     * Выводить в консоль параметры запроса (METHOD, URI, HEADERS, COOKIES, BODY)
     *
     * @param requestSpecification Спецификация запроса
     */
    public void toConsoleAllRequestParameters(RequestSpecification requestSpecification) {
        requestSpecification.log().method();
        requestSpecification.log().uri();
        requestSpecification.log().headers();
        requestSpecification.log().cookies();
        requestSpecification.log().body();
    }

    /**
     * Вернуть тело запроса на основе заготовки с вилдкартами и значениями для вилдкарт
     *
     * @param bodyFilePath   Путь к файлу заготовки тела
     * @param wildcardValues Словарь значений вилдкарт
     * @return Тело запроса
     */
    public String getBody(String bodyFilePath, Map<String, String> wildcardValues)
            throws IOException, URISyntaxException {

        // Из файла - заготовка тела с вилдкартами
        String jsonBodyTemplate = jsonProcessing.jsonFileRead(bodyFilePath);

        // Заменить вилдкарты значениями
        return jsonProcessing.wildcardReplace(jsonBodyTemplate, wildcardValues);

    }

    /**
     * POST Запрос
     * @param requestSpecification Спецификация
     * @param body Тело
     * @return Ответ на запрос
     */
    public Response post(RequestSpecification requestSpecification, String body) {
        return given().spec(requestSpecification).body(body).when().post();
    }

}

