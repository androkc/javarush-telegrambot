package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.command.CommandNameEnum;

import java.util.List;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, List<String> message);
}
