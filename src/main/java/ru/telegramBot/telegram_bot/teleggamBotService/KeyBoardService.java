package ru.telegramBot.telegram_bot.teleggamBotService;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.telegramBot.telegram_bot.entity.Client;

import java.util.ArrayList;
import java.util.List;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.*;

@Service
public class KeyBoardService {


    /**
     * кнопки  стартовой команды для клиента
     *
     * @return
     */

    public ReplyKeyboardMarkup keyboardForStartCommand() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(MENU_KEYBOARD);
        row.add(ABOUT_COURSES_KEYBOARD);
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    /**
     * прозрачные кнопки меню для клиента
     *
     * @return
     */
    public InlineKeyboardMarkup keyboardForMenu() {

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();//класс для создания прозрачной кнопки под сообщением

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();//список списков для хранения кнопок

        List<InlineKeyboardButton> rowInline = new ArrayList<>();//список с кнопками для ряда
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();//список с кнопками для ряда

        var byCourseButton = new InlineKeyboardButton();
        byCourseButton.setText("Курс со скидкой" + "\uD83D\uDC4D");
        byCourseButton.setCallbackData(BY_COURSE_BUTTON); //id

        var getDiscountButton = new InlineKeyboardButton();
        getDiscountButton.setText(" Доп скидка" + "\uD83D\uDCB8");
        getDiscountButton.setCallbackData(GET_DISCOUNT_BUTTON);

        rowInline.add(byCourseButton); //добавили кнопки в список для ряда
        rowInline.add(getDiscountButton);

        var aboutCourses = new InlineKeyboardButton();
        aboutCourses.setText("Коротко о курсе" + "\uD83D\uDC69\u200D\uD83C\uDF93");
        aboutCourses.setCallbackData(ABOUT_COURSES_BUTTON);

        var feedback = new InlineKeyboardButton();
        feedback.setText("Обратная связь" + "\uD83D\uDCDE");
        feedback.setCallbackData(FEEDBACK_BUTTON);
        rowInline1.add(aboutCourses);
        rowInline1.add(feedback);

        rowsInLine.add(rowInline); //добавили список с кнопками для ряда в список для хранения кнопок
        rowsInLine.add(rowInline1); //добавили список с кнопками для ряда в список для хранения кнопок

        markupInLine.setKeyboard(rowsInLine);// в классе меняем значения для кнопки
        return markupInLine;
    }

    /**
     * етод для создания кнопки обратная связь
     */
    public InlineKeyboardMarkup feedbackButton() {


        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();//класс для создания прозрачной кнопки под сообщением

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();//список списков для хранения кнопок

        List<InlineKeyboardButton> rowInline = new ArrayList<>();//список с кнопками для ряда


        var byCourseButton = new InlineKeyboardButton();
        byCourseButton.setText("Курс со скидкой" + "\uD83D\uDC4D");
        byCourseButton.setCallbackData(BY_COURSE_BUTTON); //id

        var feedback = new InlineKeyboardButton();
        feedback.setText("Обратная связь" + "\uD83D\uDCDE");
        feedback.setCallbackData(FEEDBACK_BUTTON);

        rowInline.add(byCourseButton);
        rowInline.add(feedback);

        rowsInLine.add(rowInline); //добавили список с кнопками для ряда в список для хранения кнопок


        markupInLine.setKeyboard(rowsInLine);// в классе меняем значения для кнопки
        return markupInLine;
    }

    /**
     * кнопка для отмены регистрации
     *
     * @return
     */
    public InlineKeyboardMarkup closeRegistrationMarkup() {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var closeRegistrationButton = new InlineKeyboardButton();
        closeRegistrationButton.setText("Прервать регистрацию");
        closeRegistrationButton.setCallbackData("/close-registration");
        rowInLine.add(closeRegistrationButton);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }

    /**
     * кнопка ок
     */
    public InlineKeyboardMarkup keyboardOk() {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var okButton = new InlineKeyboardButton();
        okButton.setText("OK");
        okButton.setCallbackData(OK_BUTTON); //id
        rowInLine.add(okButton);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }

    /**
     * кнопка закончить переписку
     *
     * @return
     */
    public InlineKeyboardMarkup endChatButton() {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var okButton = new InlineKeyboardButton();
        okButton.setText("Закончить переписку");
        okButton.setCallbackData(END_CHAT_BUTTON); //id
        rowInLine.add(okButton);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }
    /**
     *кнопки для написать клиенту
     */
    public InlineKeyboardMarkup writeToClientButton(Long chatId) {

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button = new InlineKeyboardButton();
        button.setText("написать " + chatId);
        button.setCallbackData(WRITE_TO_CLIENT_BUTTON + chatId);
        rowInLine.add(button);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }

    /**
     *кнопки для написать,изменить статус клиента,удалить клиента
     */
    public InlineKeyboardMarkup writeChangeStatusDeleteButton(Client client) {


        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button = new InlineKeyboardButton();
        button.setText("написать : " + client.getFirstName());
        button.setCallbackData(WRITE_TO_CLIENT_BUTTON + client.getChatId());

        var button2 = new InlineKeyboardButton();
        button2.setText("изменить статус : " + client.getFirstName());
        button2.setCallbackData(CHANGE_CLIENT_STATUS_BUTTON + client.getChatId());

        var button3 = new InlineKeyboardButton();
        button3.setText("удалить : " + client.getFirstName());
        button3.setCallbackData(DELETE_CLIENT_BUTTON + client.getChatId());

        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);

        return markupInLine;
    }
}


