package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.repository.TelegramUserRepository;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;

    @Override
    public void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> retrieveAllActiveUsers() {
        return telegramUserRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<TelegramUser> findByChatId(String chatId) {
        return telegramUserRepository.findById(chatId);
    }

    @Override
    public List<TelegramUser> findAllInActiveUsers() {
        return telegramUserRepository.findAllByActiveFalse();
    }

    @Override
    public List<TelegramUser> findAllActiveUsers() {
        return telegramUserRepository.findAllByActiveTrue();
    }
}
