package org.uob.a2.parser;

public class Token {
    private final TokenType type;
    private final String value;

    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getTokenType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value != null) {
            return type + ":" + value;
        }
        return type.toString();
    }
}
