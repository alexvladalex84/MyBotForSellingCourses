package ru.telegramBot.telegram_bot.teleggamBotService;


import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegramBot.telegram_bot.configuration.BotConfig;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.entity.Consultant;
import ru.telegramBot.telegram_bot.service.ClientService;
import ru.telegramBot.telegram_bot.service.ConsultantService;
import ru.telegramBot.telegram_bot.service.UserService;

import java.util.List;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.*;
import static ru.telegramBot.telegram_bot.constants.InfoAndText.*;


@Component
@EnableScheduling
public class ListenerTGBot extends TelegramLongPollingBot {

    private BotConfig botConfig;
    private final MenuTGService menuTGService;
    private final UserService userService;
    private final KeyBoardService keyBoardService;
    private final SendMethodService sendMethodService;
    private final ConsultantTGService consultantTGService;
    private final RegistrationTGService registrationTGService;
    private final ConsultantSendMessage consultantSendMessage;
    private final ConsultantKeyBoardService consultantKeyBoardService;
    private final UserTgSendMessage userTgSendMessage;
    private final Chats chats;
    private final ClientService clientService;
    private final ConsultantService consultantService;
    private final AdminTgBotService adminTgBotService;

    Logger LOG = LoggerFactory.getLogger(ListenerTGBot.class);
    //меню для бота в кострукторе


    public ListenerTGBot(SendMethodService sendMethodService, RegistrationTGService registrationTGService, BotConfig botConfig, MenuTGService menuTGService, UserService userService
            , ConsultantTGService consultantTGService, KeyBoardService keyBoardService, ConsultantSendMessage consultantSendMessage
            , ConsultantKeyBoardService consultantKeyBoardService, Chats chats, UserTgSendMessage userTgSendMessage, ClientService clientService
            , ConsultantService consultantService,AdminTgBotService adminTgBotService) {

        this.botConfig = botConfig;
        this.menuTGService = menuTGService;
        this.userService = userService;
        this.keyBoardService = keyBoardService;
        this.consultantTGService = consultantTGService;
        this.registrationTGService = registrationTGService;
        this.sendMethodService = sendMethodService;
        this.consultantSendMessage = consultantSendMessage;
        this.consultantKeyBoardService = consultantKeyBoardService;
        this.chats = chats;
        this.userTgSendMessage = userTgSendMessage;
        this.clientService = clientService;
        this.consultantService = consultantService;
        this.adminTgBotService = adminTgBotService;


        List<BotCommand> listOfCommand = menuTGService.getListOfCommands();
        try {
            this.execute(new SetMyCommands(listOfCommand, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            LOG.error("Error setting bot's command list : " + e.getMessage());
        }

    }


    @Override
    public String getBotUsername() {
        return botConfig.getNameBot();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();
            String firstName = update.getMessage().getChat().getFirstName();
            switch (messageText) {
                case START:
                    if (consultantSendMessage.commandStartForConsultant(chatId, userName) != null) {
                        executeAndCheckSendMessage(consultantSendMessage.commandStartForConsultant(chatId, userName));

                    }
                    else if (chatId == 482764411) {
                        executeAndCheckSendMessage
                                (adminTgBotService.commandStartForAdmin(482764411));
                                    executeAndCheckSendMessage
                                            (adminTgBotService.registrationConsultantTg(chatId,userName,firstName ));

                    }
                    else {
                        userService.registerUser(update.getMessage());
                        SendMessage message = sendMethodService.commandStartForClient(chatId, firstName);
                        executeAndCheckSendMessage(message);
                        consultantTGService.saveChatIdForConsultant(chatId, userName);

                    }
                    break;
                case MENU1:
                case MENU_KEYBOARD:

                    executeAndCheckSendMessage(consultantOrClient(userName, sendMethodService.sendMenuKeyBoard(chatId)));

                    break;
                case ABOUT_COURSES_KEYBOARD:
                    executeAndCheckSendMessage(sendMethodService.sendMessageAboutCourse(chatId));
                    break;
                case REGISTER:
                    executeAndCheckSendMessage(consultantOrClient(userName, registrationTGService.registration(chatId, userName, null)));

                    break;
                case HELP:
                    executeAndCheckSendMessage(sendMethodService.sendMessageHelp(chatId));
                    break;
                default:
                    if (consultantSendMessage.chatIdConsultant != null) {
                        Long chatIdConsultant = consultantSendMessage.chatIdConsultant;
                        Long chatIdOfClient = consultantSendMessage.chatIdClient;

                        try {

                            executeAndCheckListMessageText(chats.chatWisClient(chatIdOfClient, chatIdConsultant, messageText, update));

                        } catch (NullPointerException e) {
                            LOG.error(e.getMessage());
                        }

                    } else if (userTgSendMessage.chatIdConsultant != null) {
                        Long chatIdConsultant = userTgSendMessage.chatIdConsultant;
                        Long chatIdOfUser = userTgSendMessage.chatIdUser;

                        try {

                            executeAndCheckListMessageText(chats.chatWisClient(chatIdOfUser, chatIdConsultant, messageText, update));

                        } catch (NullPointerException e) {
                            LOG.error(e.getMessage());
                        }

                    } else if (!registrationTGService.registerStatus.contains(chatId)) {

                        String answer = EmojiParser.parseToUnicode(" \uD83E\uDD16 " + "Пожалуйста,сделайте рестарт бота /start");

                        executeAndCheckSendMessage(sendMethodService.sendMessageWisKeyboardForStartCommand(chatId, answer));
                    } else {
                        SendMessage sendMessage = registrationTGService.registration(chatId, userName, messageText);
                        executeAndCheckSendMessage(sendMessage);
                        if (sendMessage.getText().equals("Спасибо,регистрация завершена " + "✔\uFE0F")) {
                            executeAndCheckSendMessage(sendMethodService.sendMessageForConsultantAboutNewClient(chatId));

                        }
                    }
            }

        }
        /**
         * часть кода возвращает из апдейта нажатую кнопку(если кнопка с таким то id нажата то ...)
         */
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData(); // id кнопки в меню
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case BY_COURSE_BUTTON:
                    String text = LINK_TO_COURSE;

                    EditMessageText sendMessage = sendMethodService.getMessageCallbackQuery(chatId, messageId, text, keyBoardService.feedbackButton());

                    executeAndCheckEditMessageText(sendMessage);
                    break;

                case GET_DISCOUNT_BUTTON:
                    text = DISCOUNT_RULE_TEXT;
                    sendMessage = sendMethodService.getMessageCallbackQuery(chatId, messageId, text, keyBoardService.feedbackButton());
                    executeAndCheckEditMessageText(sendMessage);
                    break;

                case ABOUT_COURSES_BUTTON:
                    text = ABOUT_COURSES_TEXT;

                    sendMessage = sendMethodService.getMessageCallbackQuery(chatId, messageId, text, keyBoardService.feedbackButton());

                    executeAndCheckEditMessageText(sendMessage);
                    break;


                case FEEDBACK_BUTTON:
                    Client client = clientService.getByChatId(chatId);
                    if (client.getChatId() != null) {
                        executeAndCheckListMessageText(consultantSendMessage.sendMessageFeedBackConsultant(chatId));
                    } else {

                        executeAndCheckListMessageText(userTgSendMessage.sendMessageFeedBackConsultant(chatId));
                    }
                    break;


                case WAITING_DISCOUNT_BUTTON:
                    int number = 0;
                    executeAndCheckListMessageText(consultantSendMessage.getClientsByStatusWaitingDisc(chatId, number));

                    break;
                case GOD_A_DISCOUNT_BUTTON:
                    number = 1;
                    executeAndCheckListMessageText(consultantSendMessage.getClientsByStatusWaitingDisc(chatId, number));
                    break;


                default:

                        executeAndCheckListMessageText(consultantSendMessage.writeOrChangeStatusCallbackData(chatId, callbackData));


            }

        }

    }

    protected SendMessage consultantOrClient(String chatId, SendMessage sendMessage) {
        List<Consultant> consultants = consultantService.getAll();

        for (Consultant con : consultants) {
            if (con.getUserName().equals(chatId)) {
                String text = "Эта функция для клиента!";
                SendMessage message = new SendMessage();
                message.setChatId(con.getChatId());
                message.setText(text);
                return message;
            }
        }
        return sendMessage;

    }


    /**
     * вспомогательный метод для отправки сообщения с проверкой
     */
    protected void executeAndCheckSendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error("Error of sendMessage : " + e.getMessage());
        }
    }

    /**
     * вспомогательный метод для отправки списка сообщений с проверкой
     */
    protected void executeAndCheckListMessageText(List<SendMessage> messages) {
        for (SendMessage sm : messages) {
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                LOG.error("Error of sendMessage : " + e.getMessage());
            }
        }

    }

    /**
     * вспомогательный метод для отправки сообщения с проверкой для кнопок
     * update.hasCallbackQuery()
     */
    protected void executeAndCheckEditMessageText(EditMessageText message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error("Error of sendMessage : " + e.getMessage());
        }
    }


}