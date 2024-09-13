package ru.telegramBot.telegram_bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.entity.User;
import ru.telegramBot.telegram_bot.repository.UserRepository;
import ru.telegramBot.telegram_bot.teleggamBotService.ListenerTGBot;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.REGISTER;
import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.START;

@Service
public class UserService {
    private final UserRepository userRepository;
    Logger LOG = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * метод для регистрации простых посетителей бота
     * @param msg
     */
    public void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {

            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            LOG.info("user save" + user);
        }
    }

    /**
     *вернуть юзера по юзер нэйму
     */
    public User getUserByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.orElse(null);

    }

    public User getUserByChatId(Long chatId) {
        try {
           return userRepository.findByChatId(chatId).get();
        }catch (NullPointerException | NoSuchElementException e) {

            return null;

        }

    }

    public List<User> getAll() {
        return  userRepository.findAll();
    }
}
