package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.command.CommandNameEnum;

import java.util.List;

public interface SendBotMessageService {
    void sendMessage(Long chatId, String message);

    void sendMessage(Long chatId, List<String> message);
}
