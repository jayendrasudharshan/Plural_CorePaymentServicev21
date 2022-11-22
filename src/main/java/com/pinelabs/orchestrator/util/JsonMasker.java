package com.pinelabs.orchestrator.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class JsonMasker {

    @Value("${maskFields}")
    private String maskFields;

    private static final Pattern digits = Pattern.compile("\\d");
    private static final Pattern capitalLetters = Pattern.compile("[A-Z]");
    private static final Pattern nonSpecialCharacters = Pattern.compile("[^X\\s!-/:-@\\[-`{-~]");

    private static final Configuration jsonPathConfig = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .options(Option.AS_PATH_LIST, Option.SUPPRESS_EXCEPTIONS).build();

    private Set<String> blackListedKeys;
    private Set<JsonPath> blacklistedJsonPaths = new HashSet<>();

    public JsonNode mask(JsonNode target) {
        blackListedKeys = new HashSet<>(Arrays.asList(maskFields.split(",")));
        blacklistedJsonPaths = new HashSet<>();
        if (target == null)
            return null;
        Set<String> expandedWhitelistedPaths = new HashSet<>();
        for (JsonPath jsonPath : blacklistedJsonPaths) {
            if (jsonPath.isDefinite()) {
                expandedWhitelistedPaths.add(jsonPath.getPath());
            } else {
                for (JsonNode node : jsonPath.<ArrayNode>read(target, jsonPathConfig)) {
                    expandedWhitelistedPaths.add(node.asText());
                }
            }
        }
        return traverseAndMask(target.deepCopy(), expandedWhitelistedPaths, "$");
    }

    private JsonNode traverseAndMask(JsonNode target, Set<String> expandedWhitelistedPaths, String path) {
        if (target.isTextual()) {
            return new TextNode(maskString(target.asText()));
        }
        if (target.isNumber()) {
            return new TextNode(maskNumber(target.asText()));
        }

        if (target.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = target.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (blackListedKeys.contains(field.getKey())) {
                    String childPath = appendPath(path, field.getKey());
                    if (!expandedWhitelistedPaths.contains(childPath)) {
                        ((ObjectNode) target).replace(field.getKey(), traverseAndMask(field.getValue(), expandedWhitelistedPaths, childPath));
                    }
                }
            }
        }
        if (target.isArray()) {
            for (int i = 0; i < target.size(); i++) {
                String childPath = appendPath(path, i);
                if (!expandedWhitelistedPaths.contains(childPath)) {
                    ((ArrayNode) target).set(i, traverseAndMask(target.get(i), expandedWhitelistedPaths, childPath));
                }
            }
        }
        return target;
    }

    private static String appendPath(String path, String key) {
        return path + "['" + key + "']";
    }

    private static String appendPath(String path, int ind) {
        return path + "[" + ind + "]";
    }

    private static String maskString(String value) {
        String tmpMasked = digits.matcher(value).replaceAll("*");
        tmpMasked = capitalLetters.matcher(tmpMasked).replaceAll("X");
        return nonSpecialCharacters.matcher(tmpMasked).replaceAll("x");
    }

    private static String maskNumber(String value) {
        return value.replaceAll("[0-9]", "*");
    }
}