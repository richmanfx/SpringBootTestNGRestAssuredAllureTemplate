package ru.r5am.utils;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper ;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.type.CollectionType;

@Slf4j
@Component
public class YamlProcessing {

    /**
     * Вернуть список всех любых сущностей из заданного файла
     *
     * @param entitiesFileName Имя файла сущностей
     * @return Список сущностей
     */
    public <T> List<T> getEntitiesFromFile(String entitiesFileName, Class<T> entityClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, entityClass);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(entitiesFileName);
        assertThat(inputStream)
                .withFailMessage(String.format("Cannot read YAML file ‘%s‘", entitiesFileName))
                .isNotNull();
        return mapper.readValue(inputStream, listType);
    }

}
