package ru.telegramBot.telegram_bot.teleggamBotService;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.*;

@Service
public class ConsultantKeyBoardService {
    /**
     * метод для создания кнопок получкния и удаления клиентов
     */
    public InlineKeyboardMarkup getClients() {


        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();//класс для создания прозрачной кнопки под сообщением

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();//список списков для хранения кнопок

        List<InlineKeyboardButton> rowInline = new ArrayList<>();//список с кнопками для ряда


        var byCourseButton = new InlineKeyboardButton();
        byCourseButton.setText("Получили скидку");
        byCourseButton.setCallbackData(GOD_A_DISCOUNT_BUTTON); //id

        var feedback = new InlineKeyboardButton();
        feedback.setText("Ждут скидку" );
        feedback.setCallbackData(WAITING_DISCOUNT_BUTTON);


        rowInline.add(byCourseButton);
        rowInline.add(feedback);

        rowsInLine.add(rowInline); //добавили список с кнопками для ряда в список для хранения кнопок


        markupInLine.setKeyboard(rowsInLine);// в классе меняем значения для кнопки
        return markupInLine;
    }
}
