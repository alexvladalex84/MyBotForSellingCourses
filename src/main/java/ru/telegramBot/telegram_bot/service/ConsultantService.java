package ru.telegramBot.telegram_bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.telegramBot.telegram_bot.DTO.ConsultantRegisterDto;
import ru.telegramBot.telegram_bot.DTO.StatusConsultant;
import ru.telegramBot.telegram_bot.entity.Consultant;
import ru.telegramBot.telegram_bot.repository.ConsultantRepository;
import ru.telegramBot.telegram_bot.teleggamBotService.ListenerTGBot;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    Logger LOG = LoggerFactory.getLogger(ConsultantService.class);
    public ConsultantService(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    /**
     * метод для редактирования полей объекта
     * например для редактирования статуса консультанта
     * @param consultant
     */
    public void remakeConsultant(Consultant consultant) {
//      Consultant cons =  consultantRepository.findByUserName(consultant.getUserName()).get();
        consultantRepository.save(consultant);
    }
    public boolean saveConsultant(ConsultantRegisterDto consRegDto) {
        Consultant consultant = new Consultant();
        consultant.setFirstName(consRegDto.getFirstName());
        consultant.setLastName(consRegDto.getLastName());
        consultant.setUserName(consRegDto.getUserName());
        consultant.setStatusWork(consRegDto.getStatusWork());

        if (consultantRepository.findByUserName(consultant.getUserName()).isEmpty()) {
            consultantRepository.save(consultant);
            return true;
        }
        return false;
    }

    public Consultant getConsultantByUserName(String userName) {


        try {
            return consultantRepository.findByUserName(userName).get();
        } catch (NoSuchElementException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    public Consultant getConsultantByChatId(Long chatId) {
        try {
            return consultantRepository.findByChatId(chatId).get();
        } catch (NoSuchElementException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }
    public List<Consultant> getAll() {
        return consultantRepository.findAll();
    }
}
