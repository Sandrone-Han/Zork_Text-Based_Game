package org.uob.a2.commands;

public class CommandErrorException extends Exception {

    public CommandErrorException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return "CommandError: " + getMessage();
    }
}
