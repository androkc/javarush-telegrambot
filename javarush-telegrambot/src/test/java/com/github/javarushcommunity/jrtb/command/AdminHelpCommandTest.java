package com.github.javarushcommunity.jrtb.command;

import static com.github.javarushcommunity.jrtb.command.AdminHelpCommand.ADMIN_HELP_MESSAGE;
import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.ADMIN_HELP;

class AdminHelpCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return ADMIN_HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ADMIN_HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AdminHelpCommand(sendBotMessageService);
    }
}