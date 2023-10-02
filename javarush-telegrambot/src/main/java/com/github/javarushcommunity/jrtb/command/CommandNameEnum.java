package com.github.javarushcommunity.jrtb.command;

public enum CommandNameEnum {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("nocommand");

    private final String commandName;

    CommandNameEnum(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}
