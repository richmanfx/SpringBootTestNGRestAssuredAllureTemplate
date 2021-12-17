package ru.r5am.entities;

import lombok.extern.slf4j.Slf4j;
import groovy.json.JsonException;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;
import static org.assertj.core.api.Assertions.assertThat;
import io.restassured.path.json.exception.JsonPathException;

/**
* REST ответ
*/
@Slf4j
@Component
public class RestResponse {

    /**
     * Проверить JSON в ответе REST-сервиса:
     *  1. Содержит поле 'success'.
     *  2. ...
     *
     * @param response Ответ на запрос
     */
    public void jsonCheck(Response response) {
        try {
            Boolean actualSuccess = response.jsonPath().get("success");
            log.info("actualSuccess: {}", actualSuccess);
            if (!actualSuccess) {
                responseLogging(response);
            }
        } catch (JsonException | JsonPathException exception) {
            responseLogging(response);
            assertThat(true)
                    .withFailMessage(String.format("Response JSON parsing exception: '%s'", exception))
                    .isFalse();
        }
    }

    /**
     * Вывести в лог с уровнем INFO куки, хидеры и тело ответа
     * @param response Ответ на запрос
     */
    public void responseLogging(Response response) {

        // Cookies
        Cookies cookies = response.getDetailedCookies();
        log.info("Response cookies: : \n{}\n", cookies);

        // Headers
        Headers responseHeaders = response.getHeaders();
        log.info("“Response headers:\n{}\n", responseHeaders.toString());

        // Body
        log.info("Response body: \n{}\n", response.asString());
    }

}
