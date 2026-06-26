package org.uob.a2.parser;

import java.util.ArrayList;

public class Tokeniser {

    private ArrayList<Token> tokens;

    public Tokeniser() {
        this.tokens = new ArrayList<>();
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public String sanitise(String input) {
        if (input == null) return "";
        return input.trim().toLowerCase();
    }

    public ArrayList<Token> tokenise(String input) {
        tokens = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return tokens;
        }

        String[] words = input.trim().toLowerCase().split("\\s+");

        switch (words[0]) {
            case "move":
                tokens.add(new Token(TokenType.MOVE));
                break;
            case "get":
                tokens.add(new Token(TokenType.GET));
                break;
            case "drop":
                tokens.add(new Token(TokenType.DROP));
                break;
            case "use":
                tokens.add(new Token(TokenType.USE));
                break;
            case "look":
                tokens.add(new Token(TokenType.LOOK));
                break;
            case "status":
                tokens.add(new Token(TokenType.STATUS));
                break;
            case "help":
                tokens.add(new Token(TokenType.HELP));
                break;
            case "quit":
                tokens.add(new Token(TokenType.QUIT));
                break;
            case "combine":
                tokens.add(new Token(TokenType.COMBINE));
                break;
            default:
                tokens.add(new Token(TokenType.VAR, words[0]));
        }

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (word.equals("on") || word.equals("with") || word.equals("to")) {
                tokens.add(new Token(TokenType.PREPOSITION, word));
            }  else {
                tokens.add(new Token(TokenType.VAR, word));
            }
        }

        tokens.add(new Token(TokenType.EOL));

        return tokens;
    }
}
