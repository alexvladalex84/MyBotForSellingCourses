package ru.telegramBot.telegram_bot.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    /**
     * Дополнительный метод для обработки строки ввода для имени и фамилии
     * меняет первую букву на заглавную
     */
    public static String stringForNameAndLastNameToUpperCase(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();

    }

    /**
     * Дополнительный метод для обработки строки ввода для имени и фамилии
     * содержит только русскик буквы
     */
    public static String stringForNameAndLastName(String text) {

        boolean cyrillic = text.chars()
                .mapToObj(Character.UnicodeBlock::of)
                .anyMatch(b -> b.equals(Character.UnicodeBlock.CYRILLIC));


        if (cyrillic) {

            return text;

        }
        return null;
    }

    /**
     * дополнительный метод для проверки введенных символов для номера телефона
     * строкака содержит только цифры без пробелов
     */
    public static String stringForPhoneNumber(String text) {
        String str = text.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("^((\\+?7)([0-9]{10}))$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            if (!str.substring(0).equals("+")) {
                String str1 = "+" + str;
                return str1;
            }
            return str;
        }
        return null;
    }

}

