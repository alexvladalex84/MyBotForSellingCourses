package ru.telegramBot.telegram_bot.constants;

import org.springframework.stereotype.Service;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.AMOUNT_OF_DISCOUNT;
import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.LINK_TO_COURSE;

@Service
public class InfoAndText {
    public static final String HELP_TEXT = "Этот бот поможет купить курс с огромной скидкой, если перейти по этой реферальной ссылке: " + LINK_TO_COURSE + "\n"
            + "После покупки курса ты можешь получить ещё и дополнительную скидку в размере " + AMOUNT_OF_DISCOUNT + " !"+"\n\n" +
            "Внимание❗основная и дополнительная скидки действительны, если курс был приобретён по ссылке через бот ⬆ ➡" + LINK_TO_COURSE +
            "Ты можешь воспользоваться меню с такими командами как:\n\n" +
            "Type /start приветственное сообщение и выбор действий\n\n" +
            "Type /help получить данное меню снова \n\n" +
            "Type /additional discount условия для получения дополнительной скидки\n\n" +
            "Type /Register быстрая регистрация для получения дополнительной скидки";

    public static final String START_TEXT = " привет, я бот, который помогает тебе получить скидку на обучение " + " :blush:\n" +
            "После покупки курса со скидкой можно получить дополнительную скидку в размере " + AMOUNT_OF_DISCOUNT + "\uD83D\uDCB0 от доброго бота" + "\uD83E\uDD17 \n\n" +
            "для этого необходимо купить курс со скидкой по этой ссылке ➡ " + LINK_TO_COURSE + " после связаться со мной ⬇ \n" +
            "и получить дополнительную скидку " + AMOUNT_OF_DISCOUNT + "\n\n" +
            "Внимание ❗ основная и дополнительная скидки действительны если курс был приобретён по ссылке через бот ⬆ ➡" + LINK_TO_COURSE + "\n\n" +
            "Если остались вопросы, можно просто написать мне и даже договориться о созвоне и получить ответы на все вопросы о скидках и обучении \uD83D\uDE09" +
            "для этого жмакай меню и выбирай нужную кнопку ⬇";

    public static final String DISCOUNT_RULE_TEXT = "Для того, что бы получить дополнительную скидку: \n" +
            " 1.Необходимо приобрести курс именно по данной ссылке  \n" + LINK_TO_COURSE +
            " 2.После того, как курс был куплен, пройти короткую регистрацию в боте /register \n" +
            "3.Жду подтверждения от Sky.pro о купленном вами курсе  \n" +
            "4.Связываюсь с вами через телеграм (по телефону) и мы решаем, как вам удобно получить доп скидку  в размере " + AMOUNT_OF_DISCOUNT + " \n\n" +
            "Внимание ❗ основная и дополнительная скидки действительны, если курс был приобретён по ссылке через бот ⬆ ➡" + LINK_TO_COURSE;

    public static final String ABOUT_COURSES_TEXT = LINK_TO_COURSE;
}
