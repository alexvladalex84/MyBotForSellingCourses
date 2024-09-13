package ru.telegramBot.telegram_bot.configuration;

//import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Objects;


@Configuration

//@PropertySource("application.properties") //когда собирается проэкт install выдаёт ошибки
public class BotConfig {
    @Value("${telegram.bot.name}")
    String nameBot;
    @Value("${telegram.bot.token}")
    String token;
//    @Value("${telegram.bot.owner}")
//    Long ownerId;
    public BotConfig() {

    }
    public BotConfig(String nameBot, String token) {
        this.nameBot = nameBot;
        this.token = token;
    }

    public String getNameBot() {
        return nameBot;
    }

    public void setNameBot(String nameBot) {
        this.nameBot = nameBot;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BotConfig{" +
                "nameBot='" + nameBot + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotConfig botConfig = (BotConfig) o;
        return Objects.equals(nameBot, botConfig.nameBot) && Objects.equals(token, botConfig.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameBot, token);
    }
}
