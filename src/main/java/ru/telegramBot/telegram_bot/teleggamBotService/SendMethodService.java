package ru.telegramBot.telegram_bot.teleggamBotService;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.service.ClientService;
import ru.telegramBot.telegram_bot.service.ConsultantService;


import static ru.telegramBot.telegram_bot.constants.InfoAndText.*;

@Service

public class SendMethodService {

    private final KeyBoardService keyBoardService;
    private final ClientService clientService;
    protected final ConsultantService consultantService;


    Logger LOG = LoggerFactory.getLogger(SendMethodService.class);

    public SendMethodService(KeyBoardService keyBoardService, ClientService clientService, ConsultantService consultantService) {

        this.keyBoardService = keyBoardService;
        this.clientService = clientService;
        this.consultantService = consultantService;
    }

    /**
     * метод стартовой команды для клиента
     */
    protected SendMessage commandStartForClient(Long chatId, String userName) {
        String answer;
        if (userName.isEmpty()) {
            answer = EmojiParser.parseToUnicode(START_TEXT);
            return sendMessageWisKeyboardForStartCommand(chatId, answer);

        }
        answer = EmojiParser.parseToUnicode(userName + START_TEXT);
        return sendMessageWisKeyboardForStartCommand(chatId, answer);

    }

    /**
     * метод для команды HELP
     */
    protected SendMessage sendMessageHelp(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(HELP_TEXT);
        return message;
    }

    /**
     * метод для отправки сообщений c клавиатурой для главного меню
     */
    protected SendMessage sendMessageWisKeyboardForStartCommand(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(keyBoardService.keyboardForStartCommand());
        return message;
    }

    /**
     * метод для отправки сообщения для уже зарегестрированного пользователя который пытается зарегестрироваться
     */
    protected SendMessage sendMessageForRegisterClient(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(keyBoardService.feedbackButton());
        return message;
    }

    /**
     * метод для отправки сообщений c кнопкой для отмены регистрации
     */
    protected SendMessage sendMessageWisCloseRegistrationMarkup(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
//        message.setReplyMarkup(keyBoardService.closeRegistrationMarkup());
        return message;
    }

    /**
     * метод для отправки сообщений через id кнопки
     *
     * @return
     */
    protected EditMessageText getMessageCallbackQuery(Long chatId, Integer messageId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(inlineKeyboardMarkup);
        message.setMessageId(messageId);

        return message;
    }

    /**
     * метод для отправки меню с прозрачными кнопками
     */
    protected SendMessage sendMenuKeyBoard(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("⬇\uFE0F" + " Главное меню " + "⬇\uFE0F");
        message.setReplyMarkup(keyBoardService.keyboardForMenu());
        return message;
    }

    /**
     * метод для отправки сообщения о неправельном вводе номера телефона
     */
    protected SendMessage sendMessageErrorNumberPhone(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        return message;

    }

    /**
     * метод для отправки сообщения о завершённой регистрации
     */
    protected SendMessage sendMessageRegistrationCompleted(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(keyBoardService.keyboardForMenu());
        return message;
    }

    /**
     * метод для консультанта,для отправки сообщения о новом зарегестрированном клиенте в чат кольсунтанта
     */
    protected SendMessage sendMessageForConsultantAboutNewClient(Long chatId) {
        Client client = clientService.getByChatId(chatId);
        if (client.getChatId() == null) {

            LOG.error("sendMessageForConsultantAboutNewClient нет клиента");
        }
        String text = "Новый клиент : " + client.getUserName() + " зарегестрировался и ждёт доп скидку";
        SendMessage message = new SendMessage();
        message.setChatId(client.getConsultant().getChatId());
        message.setText(text);
        message.setReplyMarkup(keyBoardService.writeChangeStatusDeleteButton(client));
        return message;

    }

    /**
     * метод для отправки сообщения о курсе
     */
    protected SendMessage sendMessageAboutCourse(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(ABOUT_COURSES_TEXT);
        message.setReplyMarkup(keyBoardService.feedbackButton());
        return message;
    }


}


