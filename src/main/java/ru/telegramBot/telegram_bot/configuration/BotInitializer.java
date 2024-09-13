package ru.telegramBot.telegram_bot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.telegramBot.telegram_bot.teleggamBotService.ListenerTGBot;

@Component
public class BotInitializer {
@Autowired
    private ListenerTGBot bot;

    Logger LOG = LoggerFactory.getLogger(BotInitializer.class);

    @EventListener({ContextRefreshedEvent.class})

    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            LOG.error("Error occurred : " + e.getMessage());
        }
    }
}
