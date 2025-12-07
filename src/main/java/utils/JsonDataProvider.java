package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import models.LoginData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Utility class for reading JSON test data.
 * Supports both file paths and classpath resources.
 * Follows best practices with proper error handling and logging.
 */
public class JsonDataProvider {

    private static final Logger logger = LogManager.getLogger(JsonDataProvider.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonDataProvider() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }

    /**
     * Reads JSON test data from file path and returns as Object[][] for TestNG DataProvider
     * 
     * @param filePath Path to JSON file
     * @return Object[][] array for TestNG DataProvider
     */
    @Step("Read JSON test data from file: {filePath}")
    public static Object[][] getJsonData(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            logger.error("JSON file not found at: {}", filePath);
            throw new RuntimeException("❌ JSON file not found at: " + filePath);
        }

        try {
            JsonNode rootNode = mapper.readTree(file);

            if (!rootNode.isArray()) {
                throw new RuntimeException("❌ JSON root must be an array: " + filePath);
            }

            List<Object[]> dataList = new ArrayList<>();

            for (JsonNode node : rootNode) {
                dataList.add(new Object[]{node});
            }

            logger.info("Successfully loaded {} test data entries from {}", dataList.size(), filePath);
            return dataList.toArray(new Object[0][]);

        } catch (IOException e) {
            logger.error("Failed to parse JSON file: {}", filePath, e);
            throw new RuntimeException("❌ Failed to parse JSON file: " + filePath, e);
        }
    }

    /**
     * Reads LoginData objects from JSON file (classpath resource)
     * 
     * @param resourcePath Classpath resource path (e.g., "testdata/login_data.json")
     * @return List of LoginData objects
     */
    @Step("Load LoginData from JSON resource: {resourcePath}")
    public static List<LoginData> loadLoginData(String resourcePath) {
        try (InputStream inputStream = JsonDataProvider.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            
            if (inputStream == null) {
                logger.error("Resource not found: {}", resourcePath);
                throw new RuntimeException("❌ JSON resource not found: " + resourcePath);
            }

            List<LoginData> loginDataList = mapper.readValue(
                    inputStream,
                    new TypeReference<List<LoginData>>() {}
            );

            logger.info("Successfully loaded {} LoginData entries from {}", loginDataList.size(), resourcePath);
            return loginDataList;

        } catch (IOException e) {
            logger.error("Failed to parse JSON resource: {}", resourcePath, e);
            throw new RuntimeException("❌ Failed to parse JSON resource: " + resourcePath, e);
        }
    }

    /**
     * Converts List<LoginData> to Object[][] for TestNG DataProvider
     * 
     * @param loginDataList List of LoginData objects
     * @return Object[][] array for TestNG DataProvider
     */
    public static Object[][] toDataProviderArray(List<LoginData> loginDataList) {
        Object[][] data = new Object[loginDataList.size()][];
        for (int i = 0; i < loginDataList.size(); i++) {
            data[i] = new Object[]{loginDataList.get(i)};
        }
        return data;
    }

    @Step("Convert JSON node to Map")
    public static Map<String, Object> jsonNodeToMap(JsonNode node) {
        try {
            return mapper.convertValue(node, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to convert JSON node to Map", e);
        }
    }

    @Step("Get value from JSON node | key = {key}")
    public static String getValueFromNode(JsonNode node, String key) {
        if (node == null) return "";

        JsonNode valueNode = node.get(key);

        return valueNode != null ? valueNode.asText() : "";
    }

    @Step("Get nested value from JSON node | path = {fieldPath}")
    public static String getNestedValue(JsonNode node, String... fieldPath) {

        JsonNode current = node;
        for (String field : fieldPath) {
            if (current == null) return "";
            current = current.get(field);
        }
        return current != null ? current.asText() : "";
    }
}
