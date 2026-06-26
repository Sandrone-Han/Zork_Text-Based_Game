package org.uob.a2.commands;

import org.junit.jupiter.api.Test;
import org.uob.a2.parser.Token;
import org.uob.a2.parser.TokenType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void testCreateMoveCommand() throws CommandErrorException {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.MOVE));
        tokens.add(new Token(TokenType.VAR, "north"));

        Command command = CommandFactory.createCommand(tokens);

        assertTrue(command instanceof Move);
        assertEquals(CommandType.MOVE, command.getCommandType());
        assertEquals("north", command.value);

        System.out.println("AUTOMARK::CommandFactory.testCreateMoveCommand: PASS");
    }

    @Test
    void testCreateGetCommand() throws CommandErrorException {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.GET));
        tokens.add(new Token(TokenType.VAR, "key"));

        Command command = CommandFactory.createCommand(tokens);

        assertTrue(command instanceof Get);
        assertEquals(CommandType.GET, command.getCommandType());
        assertEquals("key", command.value);

        System.out.println("AUTOMARK::CommandFactory.testCreateGetCommand: PASS");
    }

    @Test
    void testCreateUseCommand() throws CommandErrorException {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.USE));
        tokens.add(new Token(TokenType.VAR, "key"));
        tokens.add(new Token(TokenType.PREPOSITION, "on"));
        tokens.add(new Token(TokenType.VAR, "door"));

        Command command = CommandFactory.createCommand(tokens);

        assertTrue(command instanceof Use);
        assertEquals(CommandType.USE, command.getCommandType());
        assertTrue(command.toString().contains("key on door"));

        System.out.println("AUTOMARK::CommandFactory.testCreateUseCommand: PASS");
    }

    @Test
    void testCreateLookCommandWithoutArgument() throws CommandErrorException {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.LOOK));
        tokens.add(new Token(TokenType.EOL));

        Command command = CommandFactory.createCommand(tokens);

        assertTrue(command instanceof Look);
        assertEquals(CommandType.LOOK, command.getCommandType());

        System.out.println("AUTOMARK::CommandFactory.testCreateLookCommandWithoutArgument: PASS");
    }

    @Test
    void testCreateCombineCommand() throws CommandErrorException {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.COMBINE));
        tokens.add(new Token(TokenType.VAR, "wire"));
        tokens.add(new Token(TokenType.VAR, "battery"));

        Command command = CommandFactory.createCommand(tokens);

        assertTrue(command instanceof Combine);
        assertEquals(CommandType.COMBINE, command.getCommandType());

        System.out.println("AUTOMARK::CommandFactory.testCreateCombineCommand: PASS");
    }

    @Test
    void testInvalidCommandThrowsException() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.GET));

        assertThrows(CommandErrorException.class, () -> {
            CommandFactory.createCommand(tokens);
        });

        System.out.println("AUTOMARK::CommandFactory.testInvalidCommandThrowsException: PASS");
    }
}
