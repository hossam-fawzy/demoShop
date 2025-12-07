package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonDataProvider {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonDataProvider() {}

    @Step("Read JSON test data from file: {filePath}")
    public static Object[][] getJsonData(String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
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

            return dataList.toArray(new Object[0][]);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to parse JSON file: " + filePath, e);
        }
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
