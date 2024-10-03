package ru.telegramBot.telegram_bot.teleggamBotService;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegramBot.telegram_bot.DTO.ConsultantRegisterDto;
import ru.telegramBot.telegram_bot.DTO.StatusClient;
import ru.telegramBot.telegram_bot.DTO.StatusConsultant;
import ru.telegramBot.telegram_bot.Utils.Utility;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.entity.Consultant;
import ru.telegramBot.telegram_bot.service.ConsultantService;

import java.sql.Timestamp;

@Service
public class AdminTgBotService {
    private final SendMethodService sendMethodService;
    private final ConsultantService consultantService;

    public AdminTgBotService(SendMethodService sendMethodService, ConsultantService consultantService) {
        this.sendMethodService = sendMethodService;
        this.consultantService = consultantService;
    }
    protected SendMessage commandStartForAdmin(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Привет начальник!Можешь регистрировать консультанта ⬇\uFE0F \n\n Ввести номер телефона консультанта ⬇\uFE0F");
//        message.setReplyMarkup(consultantKeyBoardService.getClients());
        return message;
    }

    protected SendMessage registrationConsultantTg(Long chatId,String userName,String firstName) {
        ConsultantRegisterDto consultant = new ConsultantRegisterDto();
        consultant.setUserName(userName);
        consultant.setFirstName(firstName);
        consultant.setStatusWork(StatusConsultant.FREE);
        consultant.setChatId(chatId);//номер отсутствует в базе
        consultantService.saveConsultant(consultant);
         SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Вы сохранены в базу как консультант!!!");
        return message;
    }
}



