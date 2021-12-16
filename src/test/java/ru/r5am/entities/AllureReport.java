package ru.r5am.entities;

import io.qameta.allure.Step;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import io.qameta.allure.Attachment;
import io.qameta.allure.AllureLifecycle;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

/**
 * Allure отчёт
 */
@Slf4j
@Component
public class AllureReport {

    /**
     * Вывести сообщение
     * @param message Текст сообщения
     */
    public void message(String message) {
        Allure.step(message);
    }

    /**
     * Вывести сообщение с дополнительной выпадающей информацией
     * @param message        Текст сообщения в отчёт
     * @param additionalInfo Сообщение для раскрывающейся вкладки "Дополнительная информация"
     */
    @Step("{0}")
    public static void dropDownMessage(String message, String additionalInfo) {
        // Сообщение "message" выводится в Аллюре отчёт через аннотацию @Step
        attach(additionalInfo);
    }

    @Attachment("Дополнительная информация")
    protected static String attach(String info) {
        return info;
    }

    /**
     * Вывести информацию о тестовом стенде
     * @param stand Стенд
     */
    public void standInfo(Stand stand) {
        message(String.format("Стенд: %s (%s:%s)", stand.name, stand.url, stand.port));
    }

    /**
     * Вывести информацию о клиенте
     * @param client Клиент
     */
    public void clientInfo(Client client) {
        message(String.format(
                "Клиент: %s %s %s", client.lastname, client.firstname, client.patronymic));
    }

    /**
     * Вывести тело запроса
     * @param body Тело запроса
     */
    public void requestBody(String body) {
        message(String.format("Тело запроса: %s", body));
    }

    /**
     * Вывести тело ответа
     * @param response Ответ
     */
    public void responseBody(Response response) {
        dropDownMessage("Тело ответа: ", response.asString());
    }

    /**
     * Прикрепить PDF файл к Аллюре отчёту
     * @param fileName    Имя файла
     * @param pdfFileData Данные файла (массив байт)
     * @return PDF файл в виде массива байт для аннотации @Attachment
     */
    @Attachment(value = "{fileName}", type = "application/pdf", fileExtension = ".pdf")
    public byte[] pdfAttach(String fileName, byte[] pdfFileData) {
        return pdfFileData;
    }

    /**
     * Заменить имя Test-метода в Аллюре отчёте
     * @param newName Новое имя метода
     */
    public void replaceTestMethodName(String newName) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateTestCase(testResult -> testResult.setName(newName));
    }

    /**
     * Заменить имя фикстуры (методы @BeforeClass, @AfterClass ...) в Аллюре отчёте
     * @param newName Новое имя фикстуры
     */
    public void replaceFixtureName(String newName) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateFixture(testResult -> testResult.setName(newName));
    }

    /**
     * Заменить имя шага в Аллюре отчёте
     * @param newName Новое имя шага
     */
    public void replaceStepName(String newName) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateStep(testResult -> testResult.setName(newName));
    }

}
