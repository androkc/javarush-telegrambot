package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.command.CommandNameEnum;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}
