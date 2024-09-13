package ru.telegramBot.telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegramBot.telegram_bot.entity.Menu;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuForBotRepository extends JpaRepository<Menu,Integer> {
    Optional<Menu> findByNameCommand(String nameCommand);
}
