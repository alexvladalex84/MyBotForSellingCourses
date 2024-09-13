package ru.telegramBot.telegram_bot.teleggamBotService;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@EnableScheduling
public class Chats {
    public final SendMethodService sendMethodService;
    public final KeyBoardService keyBoardService;
    protected final Set<Long> chatIdClients = new HashSet<>();

    public Chats(SendMethodService sendMethodService, KeyBoardService keyBoardService) {
        this.sendMethodService = sendMethodService;
        this.keyBoardService = keyBoardService;
    }

    /**
     * метод для переписки консультанта с клиентом
     */
    protected List<SendMessage> chatWisClient(Long chatIdOfClient, Long chatIdOfConsultant, String messageText, Update update) {
        List<SendMessage> messages = new ArrayList<>();
        Long updateChatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        SendMessage message = new SendMessage();

        message.setChatId(chatIdOfConsultant);


            if (updateChatId.equals(chatIdOfConsultant)) {
                SendMessage message1 = new SendMessage();
                message1.setChatId(String.valueOf(chatIdOfClient));
                message1.setText(messageText);
                SendMessage message2 = new SendMessage();
                message2.setReplyMarkup(keyBoardService.endChatButton());
                message2.setChatId(chatIdOfConsultant);
                message2.setText("Если клиент не ответил,жми ⬇\uFE0F");
                messages.add(message1);
                messages.add(message2);
                return messages;


            } else if (updateChatId.equals(chatIdOfClient)) {
                SendMessage message1 = new SendMessage();
                message1.setChatId(chatIdOfConsultant);
                message1.setText("Вам пишет ChatId : " + chatIdOfClient + " User name : " + userName + " ⬇\uFE0F \n\n " + messageText);
                message1.setReplyMarkup(keyBoardService.endChatButton());
                messages.add(message1);
                return messages;

            } else if (chatIdOfConsultant == null) {
                SendMessage message1 = new SendMessage();
                message1.setChatId(String.valueOf(chatIdOfClient));
                message1.setText("Переписка закончена");
                message1.setReplyMarkup(keyBoardService.feedbackButton());
                messages.add(message1);
                return messages;
            }




        message.setText("Чтото пошло не так !!! ");
        messages.add(message);
        return messages;
    }

//    @Scheduled(fixedDelay = 1_000l)
//    @Scheduled(cron = "0 * * * * *")
    protected SendMessage scheduledSendMessage() {
        SendMessage message = new SendMessage();

        message.setChatId(482764411l);
        message.setText("Клиент ChatId : "  + " User name : "
                + " не ответил ! \n Завершить переписку ? ⬇\uFE0F ");
        message.setReplyMarkup(keyBoardService.endChatButton());
        return message;
    }
}