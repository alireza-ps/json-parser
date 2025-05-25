package com.github.alirezaps.jsonserializer.parser;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    private final String json;
    private int index = 0;

    public JsonParser(String json) {
        this.json = json.trim();
    }

    public Map<String, Object> parse() {
        skipWhitespace();
        expect('{', "JSON must start with '{'");
        advance();

        Map<String, Object> result = new HashMap<>();
        skipWhitespace();
        if (peek() == '}') {
            advance();
            return result; // Return empty map if JSON object is empty
        }

        while (peek() != '}') {
            String key = parseString();
            skipWhitespace();
            expect(':', " Expected ':' after key");
            advance();
            skipWhitespace();
            Object value = readValue();
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

    private Object readValue() {
        char ch = peek();
        if (ch == '"') return parseString();
        else if (Character.isDigit(ch) || ch == '-') return parseNumber();
        else if (ch == 't' || ch == 'f') return parseBoolean();
        else if (ch == 'n') return parseNull();
        else throw new RuntimeException("Unexpected start of value " + ch);
    }

    private Object parseNull() {
        if (json.startsWith("null", index)) {
            index += 4; //to go at end of null
            return null;
        }
        throw new RuntimeException("Expected 'null' at index " + index);
    }

    private Boolean parseBoolean() {
        if (json.startsWith("true", index)) {
            index += 4; // to go at end of true
            return Boolean.TRUE;
        } else if (json.startsWith("false", index)) {
            index += 5; // to go at end of false
            return Boolean.FALSE;
        }
        throw new RuntimeException("Expected true or false at index " + index);
    }

    private Number parseNumber() {
        int start = index;
        if (peek() == '-')
            advance();

        while (index < json.length() && Character.isDigit(peek())) {
            advance();
            if (index < json.length() && peek() == '.') {
                advance();
                while (index < json.length() && Character.isDigit(peek())) advance();
                return Double.parseDouble(json.substring(start, index));
            }
        }
        return Long.parseLong(json.substring(start, index));
    }

    private void skipWhitespace() {
        while (index < json.length() && Character.isWhitespace(peek())) {
            advance();
        }
    }

    private String parseString() {
        expect('"', "Expected '\"' at the start of string");
        advance();
        StringBuilder sb = new StringBuilder();
        while (peek() != '"') {
            if (peek() == '\\') {
                advance();
                char escaped = peek();
                char result = switch (escaped) {
                    case '"' -> '"';
                    case '\\' -> '\\';
                    case 'n' -> '\n';
                    case 't' -> '\t';
                    default -> throw new RuntimeException("Unsupported escape character: \\" + escaped);
                };
                sb.append(result);
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
