package com.github.alirezaps.jsonserializer.parser;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    private final String json;
    private int index = 0;

    public JsonParser(String json) {
        this.json = json.trim();
    }

    public Map<String, String> parse() {
        Map<String, String> result = new HashMap<>();

        expect('{', "JSON must start with '{'");
        advance();
        skipWhitespace();

        while (peek() != '}') {
            String key = parseString();
            skipWhitespace();
            expect(':', " Expected ':' after key");
            advance();
            skipWhitespace();
            String value = parseString();
            result.put(key, value);
            skipWhitespace();

            if (peek() == ',') {
                advance();
                skipWhitespace();
            } else if (peek() != '}') {
                throw new RuntimeException("Expected ',' or '}'");
            }
        }

        advance();
        return result;
    }

    private void skipWhitespace() {
        while (index < json.length() && Character.isWhitespace(peek())) {
            advance();
        }
    }

    public String parseString() {
        expect('"', "Expected '\"' at the start of string");
        advance();
        StringBuilder sb = new StringBuilder();
        while (peek() != '"') {
            if (peek() == '\\') {
                advance();
                char escaped = peek();
                if (escaped == '"' || escaped == '\\') {
                    sb.append(escaped);
                } else {
                    throw new RuntimeException("Unsupported escape character");
                }
            } else {
                sb.append(peek());
            }
            advance();
        }
        advance();
        return sb.toString();
    }

    private char peek() {
        return json.charAt(index);
    }

    private void expect(char c, String message) {
        if (peek() != c)
            throw new RuntimeException(message);
    }

    private void advance() {
        index++;
    }
}
