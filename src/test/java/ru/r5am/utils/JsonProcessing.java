package ru.r5am.utils;

import java.util.Map;
import java.util.Objects;
import java.io. IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import java.net.URISyntaxException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonProcessing {

    /**
     * Вернуть файл JSON в виде строки
     * @param path Путь к файлу
     * @return JSON строка
     */
    public String jsonFileRead(String path) throws IOException, URISyntaxException {

        String jsonString = "";
        if (null != path && !path.trim().isEmpty()) {

            // Заменить обратные слеши в пути (для Jenkins)
            if (path.contains("\\")) {
                path = path.replace("\\\\", "/");
            }

            // Читать
            jsonString = new String(Files.readAllBytes(
                    Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(path)).toURI())));

            // Чистить для SOWA
            jsonString = jsonString.replace("\r", "").replace("\n", "").replace(" ", "");

        } else {
            log.error("File ‘{}' not found", path);
        }

        return jsonString;
    }

    /**
     * Заменить вилдкарты значениями
     * @param json JSON
     * @param wildcardValues Словарь вилдкарт и их значений
     * @return JSON с значениями вместо вилдкарт
     */
    public String wildcardReplace(String json, Map<String, String> wildcardValues) {
        for (Map.Entry<String, String> entry: wildcardValues.entrySet()) {
            json = json.replace(entry.getKey(), entry.getValue());
        }
        return json;
    }

}
