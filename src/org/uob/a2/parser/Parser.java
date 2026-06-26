package org.uob.a2.parser;

import java.util.ArrayList;

import org.uob.a2.commands.Command;
import org.uob.a2.commands.CommandErrorException;
import org.uob.a2.commands.CommandFactory;

/**
 * The Parser class converts a list of tokens into a command object.
 *
 * <p>
 * The Parser no longer creates concrete command objects directly.
 * Instead, it delegates command creation to CommandFactory.
 * This reduces coupling between the parser and individual command classes.
 * </p>
 */
public class Parser {

    public Command parse(ArrayList<Token> tokens) throws CommandErrorException {
        return CommandFactory.createCommand(tokens);
    }
}