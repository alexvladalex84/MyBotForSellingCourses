package ru.telegramBot.telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.entity.Menu;

import java.util.Optional;

@Service
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByUserName(String userName);
    Optional<Client> findByChatId(Long userName);
}
