package ru.telegramBot.telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegramBot.telegram_bot.entity.Consultant;

import java.util.Optional;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
    Optional<Consultant> findByUserName(String userName);
    Optional<Consultant> findByChatId(Long userName);


}
