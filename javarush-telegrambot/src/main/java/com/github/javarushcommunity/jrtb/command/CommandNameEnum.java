package com.github.javarushcommunity.jrtb.command;

public enum CommandNameEnum {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("no_command"),
    STAT("/stat"),
    ADD_GROUP_SUB("/add_group_sub"),
    LIST_GROUP_SUB("/list_group_sub"),
    DELETE_GROUP_SUB("/delete_group_sub"),
    ADMIN_HELP("/ahelp");


    private final String commandName;

    CommandNameEnum(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}
