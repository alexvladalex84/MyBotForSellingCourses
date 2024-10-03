package ru.telegramBot.telegram_bot.teleggamBotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegramBot.telegram_bot.DTO.StatusClient;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.entity.Consultant;
import ru.telegramBot.telegram_bot.entity.User;
import ru.telegramBot.telegram_bot.service.ClientService;
import ru.telegramBot.telegram_bot.service.ConsultantService;
import ru.telegramBot.telegram_bot.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.telegramBot.telegram_bot.constants.ConstantButtonMenuAndCommand.*;

@Service
public class ConsultantSendMessage {
    private final ConsultantService consultantService;
    private final ConsultantKeyBoardService consultantKeyBoardService;
    private final ClientService clientService;
    private final KeyBoardService keyBoardService;
    private final UserService userService;
    private final UserTgSendMessage userTgSendMessage;
    protected Long chatIdConsultant;
    protected Long chatIdClient;
    protected Map<Long, Consultant> mapConsultants = new HashMap();
    Logger LOG = LoggerFactory.getLogger(ConsultantSendMessage.class);

    public ConsultantSendMessage(ConsultantService consultantService, ConsultantKeyBoardService consultantKeyBoardService
            , ClientService clientService, KeyBoardService keyBoardService, UserService userService, UserTgSendMessage userTgSendMessage) {
        this.consultantService = consultantService;
        this.consultantKeyBoardService = consultantKeyBoardService;
        this.clientService = clientService;
        this.keyBoardService = keyBoardService;
        this.userService = userService;
        this.userTgSendMessage = userTgSendMessage;
    }

    /**
     * стартовая команда для консультанта
     */
    public SendMessage commandStartForConsultant(Long chatId, String userName) {

        Consultant consultant = consultantService.getConsultantByUserName(userName);
        if (consultant != null ) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Привет консультант!Можешь проверить кто ещё не получил скидку ⬇\uFE0F");
            message.setReplyMarkup(consultantKeyBoardService.getClients());
            return message;
        }
        return null;
    }

    /**
     * дополнительный метод консультанта для переписки с клиентом
     * https://t.me/
     */
    private SendMessage writeToClient(Long chatId, Client client) {

        SendMessage message3 = new SendMessage();
        message3.setChatId(chatId);
        message3.setText("Связаться через : , " + HTTPS_TELEGRAM + client.getUserName() + " \n "
                + " по телефону : " + client.getNumberPhone() + "\n"
                + "в чате бота ⤵ ");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button = new InlineKeyboardButton();
        button.setText("Написать в чате бота " + client.getFirstName());
        button.setCallbackData(CHAT_TO_CLIENT_BUTTON + client.getChatId());
        rowInLine.add(button);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);


        message3.setReplyMarkup(markupInLine);

        return message3;
    }


    /**
     * дополнительный метод консультанта, для изменения статуса скидки у клиента
     */
    private SendMessage changeStatusClient(Long chatId, Client client) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (client.getStatusDiscount().equals(StatusClient.GOT_A_DISCOUNT)) {
            client.setStatusDiscount(StatusClient.WAITING_DISCOUNT);
            clientService.saveClient(client);
            message.setText("Статус клиента : " + client.getFirstName() + " " + client.getLastName() + " изменён на :" + StatusClient.WAITING_DISCOUNT);
            return message;
        } else if (client.getStatusDiscount().equals(StatusClient.WAITING_DISCOUNT)) {
            client.setStatusDiscount(StatusClient.GOT_A_DISCOUNT);
            clientService.saveClient(client);
            message.setText("Статус клиента : " + client.getFirstName() + " " + client.getLastName() + " изменён на :" + StatusClient.GOT_A_DISCOUNT);
            return message;
        }
        return message;
    }

    private SendMessage deleteClient(Long chatId, Client cl) {
        SendMessage message = new SendMessage();

        clientService.deleteClient(cl);
        message.setChatId(chatId);
        message.setText("Клиент : " + cl.getFirstName() + " удалён");
        return message;

    }

    /**
     * метод консультанта, для кнопок "Написать клиенту" ,"Изменить статус скидки,удалить клиента"
     */
    protected List<SendMessage> writeOrChangeStatusCallbackData(Long chatId, String callbackData) {
        List<SendMessage> messages = new ArrayList<>();
        List<Client> clients = clientService.getAll();

        SendMessage message = new SendMessage();
        SendMessage message1 = new SendMessage();
        message.setChatId(chatId);
        message.setText("ERROR");
        for (Client cl : clients) {


            if (callbackData.equals(WRITE_TO_CLIENT_BUTTON + cl.getChatId())) {

                messages.add(writeToClient(chatId, cl));
                return messages;

            } else if (callbackData.equals(CHANGE_CLIENT_STATUS_BUTTON + cl.getChatId())) {

                messages.add(changeStatusClient(chatId, cl));
                return messages;
            } else if (callbackData.equals(DELETE_CLIENT_BUTTON + cl.getChatId())) {

                SendMessage message2 = new SendMessage();
                message2.setChatId(cl.getChatId());
                message2.setText("Бот удалил вас \uD83D\uDC94 но вы можете зарегестрироваться заново " + REGISTER);
                messages.add(deleteClient(chatId, cl));
                messages.add(message2);
                return messages;
            } else if (callbackData.equals(CHAT_TO_CLIENT_BUTTON + cl.getChatId())) {
                chatIdConsultant = chatId;
                Consultant consultant = consultantService.getConsultantByChatId(chatId);
                mapConsultants.put(chatId, consultant);

                chatIdClient = cl.getChatId();
                message.setChatId(chatIdConsultant);
                message.setText("Отлично,можете начать чат с " + cl.getFirstName() + "⤵");

                message1.setChatId(chatIdClient);
                message1.setText("Консультант уже пишет вам!");

                messages.add(message);
                messages.add(message1);
                return messages;
            } else if (callbackData.equals(END_CHAT_BUTTON)) {
                String text = "Переписка завершена";
                if (chatIdClient == null) {
                    return userTgSendMessage.writeToUserCallbackData(chatId,callbackData);
                }
                message.setChatId(chatIdClient);
                message.setText(text);
                message.setReplyMarkup(keyBoardService.feedbackButton());

                message1.setChatId(chatIdConsultant);
                message1.setText(text);

                messages.add(message);
                messages.add(message1);
                chatIdConsultant = null;
                chatIdClient = null;

                return messages;


            }


        }

        return userTgSendMessage.writeToUserCallbackData(chatId,callbackData);
    }

    /**
     * метод для отправки сообщения консультанту о просьбе связаться с клиентом или с не зарегистрированным клиентом
     */
    protected List<SendMessage> sendMessageFeedBackConsultant(Long chatId) {
        List<SendMessage> messages = new ArrayList<>();
        Client client = clientService.getByChatId(chatId);

        Long chatIDConsultant = client.getConsultant().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatIDConsultant);
        message.setText("✔ Зарегестрированный клиент " + client.getFirstName() + " просит связаться с ним");


        message.setReplyMarkup(keyBoardService.writeToClientButton(chatId));

        SendMessage message1 = new SendMessage();
        message1.setChatId(chatId);
        message1.setText("Ждём свободного консультанта \uD83D\uDCAC");
        messages.add(message);
        messages.add(message1);
        return messages;
    }

    /**
     * метод для консультанта , для создания списка с клиентами
     * возврвщает лист с SendMessage с прозрачной кнопкой в низу "Написать или изменить статус клиента"
     */
    protected List<SendMessage> sendMessageList(Long chatId, String text, List<Client> cl) {
        List<SendMessage> messages = new ArrayList<>();
        for (Client client : cl) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(text + "ИМЯ ЮЗЕРА В ТЕЛЕГРАМ : " + client.getUserName() + "\n"
                    + "ИМЯ : " + client.getFirstName() + " \n "
                    + "ФАМИЛМЯ : " + client.getLastName() + " \n "
                    + "СТАТУС : " + client.getStatusDiscount() + "\n"
                    + "ДАТА РЕГИСТРАЦИИ : " + client.getRegisteredAt());


            sendMessage.setReplyMarkup(keyBoardService.writeChangeStatusDeleteButton(client));


            messages.add(sendMessage);
        }
        return messages;

    }

    /**
     * перегруженный метод sendMessageList(Long chatId, String text, List<Client> cl),принимает в параметры
     * chatId и text
     */
    protected List<SendMessage> sendMessageList(Long chatId, String text) {
        List<SendMessage> messages = new ArrayList<>();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        messages.add(sendMessage);
        return messages;
    }

    /**
     * получить всех клиентов со статусом WAITING_DISCOUNT или GOT_A_DISCOUNT
     */
    public List<SendMessage> getClientsByStatusWaitingDisc(Long chatId, int number) {
        if (number == 0) {
            List<Client> cl = clientService.getAll().stream()

                    .filter(client -> client.getStatusDiscount().equals(StatusClient.WAITING_DISCOUNT))
                    .collect(Collectors.toList());
            if (cl.isEmpty()) {
                String text = "По вашему запросу ни чего не найдено ❌ \n";

                return sendMessageList(chatId, text);
            }
            String text = "Ждёт скидку \uD83D\uDCAC \n";
            return sendMessageList(chatId, text, cl);

        } else if (number == 1) {
            List<Client> cl = clientService.getAll().stream()
                    .filter(client -> client.getStatusDiscount().equals(StatusClient.GOT_A_DISCOUNT))
                    .collect(Collectors.toList());

            if (cl.isEmpty()) {
                String text = "По вашему запросу ни чего не найдено \uD83D\uDC4C \n";

                return sendMessageList(chatId, text);
            }
            String text = "Получил скидку \uD83D\uDC4C \n";
            return sendMessageList(chatId, text, cl);
        } else if (number == 2) {
            List<Client> cl = clientService.getAll();


            if (cl.isEmpty()) {
                String text = "По вашему запросу ни чего не найдено \uD83D\uDC4C \n";

                return sendMessageList(chatId, text);
            }
            String text = "Все клиенты ‼\uFE0F \n";
            return sendMessageList(chatId, text, cl);

        } else {

            return null;
        }

    }

}
