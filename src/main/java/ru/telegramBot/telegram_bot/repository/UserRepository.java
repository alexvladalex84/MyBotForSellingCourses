package ru.telegramBot.telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegramBot.telegram_bot.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String name);
    Optional<User> findUserByChatId(Long name);
    Optional<User> findByChatId(Long name);
}
