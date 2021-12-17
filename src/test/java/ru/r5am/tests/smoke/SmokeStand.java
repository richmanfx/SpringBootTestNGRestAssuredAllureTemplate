package ru.r5am.tests.smoke;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.r5am.entities.Stand;
import ru.r5am.basetest.ApiBaseTest;
import ru.r5am.entities.RestRequest;

import java.io.IOException;

/**
 * Smoke-тесты стенда
 */
@Slf4j
@Owner("Ящук Александр Юрьевич")
public class SmokeStand extends ApiBaseTest {

    @Autowired private Stand stand;
    @Autowired private RestRequest restRequest;
    @Value("${servers.testStand}") String standName;

    @BeforeClass
    @Description("Получение параметров стенда")
    public void getStand() throws IOException {
        log.info("\n\nStart method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        stand.get(standName, standsDataFilePath);
        standUri = "http://" + stand.url + ":" + stand.port;
        log.info("{}", stand);
    }

    @Test
    @Description("Проверка доступности стенда")
    public void standAvialabilityCheck() {
        log.info("\n\nStart method: {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        log.info("Stand URI: {}", restRequest.getBaseUri());
        allureReport.message(String.format("Стенд URI: %s", restRequest.getBaseUri()));
    }

}
