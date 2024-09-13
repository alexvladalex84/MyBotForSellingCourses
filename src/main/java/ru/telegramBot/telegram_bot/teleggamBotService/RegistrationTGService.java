package ru.telegramBot.telegram_bot.teleggamBotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegramBot.telegram_bot.DTO.StatusClient;
import ru.telegramBot.telegram_bot.Utils.Utility;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.repository.ClientListRepositoryForRegister;
import ru.telegramBot.telegram_bot.service.ClientService;
import ru.telegramBot.telegram_bot.service.ConsultantService;
import ru.telegramBot.telegram_bot.service.UserService;

import java.sql.Timestamp;
import java.util.*;

@Service
public class RegistrationTGService {
    private final ClientService clientService;
    private final ConsultantService consultantService;
    private final UserService userService;
    private final ConsultantTGService consultantTGService;
    private final KeyBoardService keyBoardService;
    private final SendMethodService sendMethodService;
    private final ClientListRepositoryForRegister clientListRepositoryForRegister;

    protected final Set<Long> registerStatus = new HashSet<>();
    protected final Map<Long, Client> newClients = new HashMap<>();

    Logger LOG = LoggerFactory.getLogger(RegistrationTGService.class);

    public RegistrationTGService(ClientService clientService, ConsultantService consultantService, UserService userService, ConsultantTGService consultantTGService, KeyBoardService keyBoardService, SendMethodService sendMethodService, ClientListRepositoryForRegister clientListRepositoryForRegister) {
        this.clientService = clientService;
        this.consultantService = consultantService;
        this.userService = userService;
        this.consultantTGService = consultantTGService;
        this.keyBoardService = keyBoardService;
        this.sendMethodService = sendMethodService;
        this.clientListRepositoryForRegister = clientListRepositoryForRegister;

    }

    /**
     * Метод для регистрации клиента который ждёт доп скидку
     */
    protected SendMessage registration(Long chatId, String userName, String text) {

        Client client = clientService.getByChatId(chatId);
        if (client.getChatId() != null) {

            text = "Регистрация уже пройдена!\nДля изменения персональных данных, пожалуйста, свяжитесь с оператором!";
            return sendMethodService.sendMessageForRegisterClient(chatId, text);

        }


        client.setChatId(chatId);
        client.setUserName(userName);

        clientListRepositoryForRegister.saveClientMap(client);

        Client client1 = clientListRepositoryForRegister.getClientRegisterMap(chatId);




            if (client1.getFirstName() == null) {
                if (text == null) {

                    registerStatus.add(chatId);
                    text = "Введите, пожалуйста, Ваше имя " + "\uD83D\uDCDD";
                    return sendMethodService.sendMessageWisCloseRegistrationMarkup(chatId, text);
                }


                String str = Utility.stringForNameAndLastName(text);
                if (str == null) {
                    LOG.error("Имя введено не корректно");
                    text = "Имя введено не корректно " + "‼\uFE0F" + "\n" + " можно вводить только русские буквы " + "\n" + "Пример : Иван ";
                    return sendMethodService.sendMessageWisCloseRegistrationMarkup(chatId, text);
                }


                Utility.stringForNameAndLastNameToUpperCase(text);
                client1.setFirstName(text);
                clientListRepositoryForRegister.saveClientMap(client1);

                text = "Введите, пожалуйста, вашу фамилию " + "\uD83D\uDCDD";
                LOG.error("после сохранения имени");
                return sendMethodService.sendMessageWisCloseRegistrationMarkup(chatId, text);
            }
         else if (client1.getLastName() == null) {


            if (text != null) {
                String str = Utility.stringForNameAndLastName(text);
                if (str == null) {
                    LOG.error("Фамилия введена не корректно");
                    text = "Фамилия введена не корректно " + "‼\uFE0F" + "\n" + " можно вводить только русские буквы " + "\n" + "Пример : Иванов ";
                    return sendMethodService.sendMessageWisCloseRegistrationMarkup(chatId, text);
                }
                Utility.stringForNameAndLastNameToUpperCase(text);
                client1.setLastName(text);
                clientListRepositoryForRegister.saveClientMap(client1);
                text = "Введите, пожалуйста, ваш номер телефона " + "\uD83D\uDCDE" + "\n" + "пример : 7 999 000 11 22 \n" + "пробелы и плюс не обязательно" + "❕";
                return sendMethodService.sendMessageWisCloseRegistrationMarkup(chatId, text);


            }
        } else if (client1.getNumberPhone() == null) {


            if (text != null) {


                String str = Utility.stringForPhoneNumber(text);

                if (str == null) {
                    LOG.error("Номер телефона введён не корректно");
                    text = ("Номер телефона " + "\uD83D\uDC49" + text + " введён не корректно " + "‼\uFE0F" + "\n" + "пример : 7 999 000 11 22 \n" + "пробелы и плюс не обязательно" + "❕");

                    return sendMethodService.sendMessageErrorNumberPhone(chatId, text);

                } else {
                    client.setFirstName(client1.getFirstName());
                    client.setLastName(client1.getLastName());
                    client.setNumberPhone(str);
                    client.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
                    client.setStatusDiscount(StatusClient.WAITING_DISCOUNT);
                    client.setConsultant(consultantTGService.consultantForClient());
                    clientService.saveClient(client);
                    clientListRepositoryForRegister.deleteClientMap(client1.getChatId());
                    text = "Спасибо,регистрация завершена " + "✔\uFE0F";
                    return sendMethodService.sendMessageRegistrationCompleted(chatId, text);

                }
            }

        }
        return sendMethodService.commandStartForClient(chatId,userName);
    }

}










