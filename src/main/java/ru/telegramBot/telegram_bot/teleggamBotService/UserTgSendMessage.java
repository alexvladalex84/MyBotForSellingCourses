package ru.telegramBot.telegram_bot.teleggamBotService;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegramBot.telegram_bot.entity.User;
import ru.telegramBot.telegram_bot.service.ConsultantService;
import ru.telegramBot.telegram_bot.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.*;

@Service
public class UserTgSendMessage {
    private final UserService userService;
    private final KeyBoardService keyBoardService;
    private final ConsultantService consultantService;
    protected Long chatIdConsultant;
    protected Long chatIdUser;


    public UserTgSendMessage(UserService userService, KeyBoardService keyBoardService, ConsultantService consultantService) {
        this.userService = userService;
        this.keyBoardService = keyBoardService;
        this.consultantService = consultantService;
    }

    private SendMessage writeToUser(Long chatIdConsultant, User user) {

        SendMessage message3 = new SendMessage();
        message3.setChatId(chatIdConsultant);
        message3.setText("Связаться через : , " + HTTPS_TELEGRAM + user.getUserName() + " \n "

                + "в чате бота ⤵ ");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button = new InlineKeyboardButton();
        button.setText("Написать в чате бота " + user.getFirstName());
        button.setCallbackData(CHAT_TO_CLIENT_BUTTON + user.getChatId());
        rowInLine.add(button);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);


        message3.setReplyMarkup(markupInLine);

        return message3;
    }

    /**
     * метод консультанта, для кнопок "Написать незарегестрированному клиенту"
     */
    protected List<SendMessage> writeToUserCallbackData(Long chatIdConsult, String callbackData) {
        List<SendMessage> messages = new ArrayList<>();
        SendMessage message = new SendMessage();
        SendMessage message1 = new SendMessage();
        message.setChatId(chatIdConsult);
        message.setText("ERRORUser");
        List<User> users = userService.getAll();
        for (User us : users) {
            if (callbackData.equals(WRITE_TO_CLIENT_BUTTON + us.getChatId())) {
                messages.add(writeToUser(chatIdConsult, us));
                return messages;
            } else if (callbackData.equals(CHAT_TO_CLIENT_BUTTON + us.getChatId())) {

                chatIdConsultant = chatIdConsult;
                chatIdUser = us.getChatId();

                message.setChatId(chatIdConsultant);
                message.setText("Отлично,можете начать чат с  ⤵");

                message1.setChatId(chatIdUser);
                message1.setText("Консультает освободился и уже пишет вам ✍\uD83C\uDFFB");
                messages.add(message);
                messages.add(message1);
                return messages;
            } else if (callbackData.equals(END_CHAT_BUTTON)) {
                String text = "Спасибо за обращение,переписка завершена \uD83E\uDD1D \n " +
                        "Если остались вопросы,жми ⤵";
                String text2 = "Переписка завершена";


                message.setChatId(chatIdUser);
                message.setText(text);
                message.setReplyMarkup(keyBoardService.feedbackButton());


                message1.setChatId(chatIdConsult);
                message1.setText(text2);
                messages.add(message);
                messages.add(message1);
                chatIdConsultant = null;
                chatIdUser = null;
                return messages;


            }


        }
        return messages;

    }

    protected List<SendMessage> sendMessageFeedBackConsultant(Long chatId) {
        List<SendMessage> messages = new ArrayList<>();


        SendMessage message = new SendMessage();
        message.setChatId(consultantService.getConsultantByUserName("alex211184").getChatId());
        message.setText("❗ Не зарегестрированный клиент просит связаться с ним \uD83D\uDCAC");


        message.setReplyMarkup(keyBoardService.writeToClientButton(chatId));


        SendMessage message1 = new SendMessage();
        message1.setChatId(chatId);
        message1.setText("Ждём свободного консультанта \uD83D\uDCAC");
        messages.add(message);
        messages.add(message1);
        return messages;


    }
}