package ru.telegramBot.telegram_bot.teleggamBotService;

import org.springframework.stereotype.Service;
import ru.telegramBot.telegram_bot.DTO.StatusConsultant;
import ru.telegramBot.telegram_bot.entity.Consultant;
import ru.telegramBot.telegram_bot.service.ClientService;
import ru.telegramBot.telegram_bot.service.ConsultantService;
import ru.telegramBot.telegram_bot.service.UserService;

import java.util.List;

@Service
public class ConsultantTGService {
    private final ConsultantService consultantService;
    private final UserService userService;
    private final KeyBoardService keyBoardService;
    private final ClientService clientService;
    private final SendMethodService sendMethodService;
    private final ConsultantSendMessage consultantSendMessage;

    public ConsultantTGService(ConsultantService consultantService, UserService userService, KeyBoardService keyBoardService
            , ClientService clientService, SendMethodService sendMethodService,ConsultantSendMessage consultantSendMessage) {
        this.consultantService = consultantService;
        this.userService = userService;
        this.keyBoardService = keyBoardService;
        this.clientService = clientService;
        this.sendMethodService = sendMethodService;
        this.consultantSendMessage = consultantSendMessage;
    }

    /**
     * метод для поиска подходящего консультанта для клиента который зарегестрировался
     *
     * @return
     */
    public Consultant consultantForClient() {

        List<Consultant> consultants = consultantService.getAll();
        for (Consultant e : consultants) {
            if (e.getStatusWork() == StatusConsultant.FREE ) {
                e.setStatusWork(StatusConsultant.BUSY);
                consultantService.remakeConsultant(e);
                return e;

            } else if (e.getStatusWork() == StatusConsultant.BUSY) {

                return e;

            }
        }
        return null;
    }

    /**
     * метод для получения chatId для консультаета для того чтобы консультант смог получать уведомления в свой чат
     */
    public void saveChatIdForConsultant(Long chatId, String userName) {
        List<Consultant> consultants = consultantService.getAll();

        for (Consultant e : consultants) {
            if (e.getUserName().equals(userName)) {
                e.setChatId(chatId);
                consultantService.remakeConsultant(e);


            }
        }
    }


    /**
     * заменить статус занятости консультанта
     *
     * @return
     */
    public void changeStatus(String userName) {
        Consultant consultant = consultantService.getConsultantByUserName(userName);
        if (consultant != null && consultant.getStatusWork().equals(StatusConsultant.BUSY)) {
            consultant.setStatusWork(StatusConsultant.FREE);

        }

    }



}


