package org.uob.a2.commands;

import java.util.ArrayList;

import org.uob.a2.parser.Token;
import org.uob.a2.parser.TokenType;

/**
 * Factory class responsible for creating Command objects from parsed tokens.
 *
 * <p>
 * This class centralises the creation logic for all command objects.
 * The Parser no longer needs to directly instantiate concrete command classes
 * such as Move, Get, Drop, or Use.
 * </p>
 */
public class CommandFactory {

    public static Command createCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new CommandErrorException("Empty command");
        }

        Token firstToken = tokens.get(0);
        TokenType commandType = firstToken.getTokenType();

        if (commandType == TokenType.MOVE) {
            return createMoveCommand(tokens);
        } else if (commandType == TokenType.GET) {
            return createGetCommand(tokens);
        } else if (commandType == TokenType.DROP) {
            return createDropCommand(tokens);
        } else if (commandType == TokenType.USE) {
            return createUseCommand(tokens);
        } else if (commandType == TokenType.LOOK) {
            return createLookCommand(tokens);
        } else if (commandType == TokenType.STATUS) {
            return createStatusCommand(tokens);
        } else if (commandType == TokenType.HELP) {
            return createHelpCommand(tokens);
        } else if (commandType == TokenType.QUIT) {
            return createQuitCommand(tokens);
        } else if (commandType == TokenType.COMBINE) {
            return createCombineCommand(tokens);
        } else {
            throw new CommandErrorException("Invalid command");
        }
    }

    private static Command createMoveCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() < 2 || tokens.get(1).getTokenType() != TokenType.VAR) {
            throw new CommandErrorException("Invalid MOVE command format");
        }

        return new Move(tokens.get(1).getValue());
    }

    private static Command createGetCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() < 2 || tokens.get(1).getTokenType() != TokenType.VAR) {
            throw new CommandErrorException("Invalid GET command format");
        }

        return new Get(tokens.get(1).getValue());
    }

    private static Command createDropCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() < 2 || tokens.get(1).getTokenType() != TokenType.VAR) {
            throw new CommandErrorException("Invalid DROP command format");
        }

        return new Drop(tokens.get(1).getValue());
    }

    private static Command createUseCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() < 4 ||
                tokens.get(1).getTokenType() != TokenType.VAR ||
                tokens.get(2).getTokenType() != TokenType.PREPOSITION ||
                tokens.get(3).getTokenType() != TokenType.VAR) {
            throw new CommandErrorException("Invalid USE command format");
        }

        return new Use(tokens.get(1).getValue(), tokens.get(3).getValue());
    }

    private static Command createLookCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() == 1 || tokens.get(1).getTokenType() == TokenType.EOL) {
            return new Look(null);
        }

        if (tokens.get(1).getTokenType() == TokenType.VAR) {
            return new Look(tokens.get(1).getValue());
        }

        throw new CommandErrorException("Invalid LOOK command format");
    }

    private static Command createStatusCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() == 1 || tokens.get(1).getTokenType() == TokenType.EOL) {
            return new Status("player");
        }

        if (tokens.get(1).getTokenType() == TokenType.VAR) {
            return new Status(tokens.get(1).getValue());
        }

        throw new CommandErrorException("Invalid STATUS command format");
    }

    private static Command createHelpCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() == 1 || tokens.get(1).getTokenType() == TokenType.EOL) {
            return new Help(null);
        }

        if (tokens.get(1).getTokenType() == TokenType.VAR) {
            return new Help(tokens.get(1).getValue());
        }

        throw new CommandErrorException("Invalid HELP command format");
    }

    private static Command createQuitCommand(ArrayList<Token> tokens) {
        return new Quit();
    }

    private static Command createCombineCommand(ArrayList<Token> tokens) throws CommandErrorException {
        if (tokens.size() < 3 ||
                tokens.get(1).getTokenType() != TokenType.VAR ||
                tokens.get(2).getTokenType() != TokenType.VAR) {
            throw new CommandErrorException("Invalid COMBINE command format. Expected: combine <item1> <item2>");
        }

        String item1 = tokens.get(1).getValue();
        String item2 = tokens.get(2).getValue();

        return new Combine(item1, item2);
    }
}