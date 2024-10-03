//package ru.telegramBot.telegram_bot.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.telegramBot.telegram_bot.DTO.ConsultantRegisterDto;
//import ru.telegramBot.telegram_bot.entity.Consultant;
//import ru.telegramBot.telegram_bot.entity.Menu;
//import ru.telegramBot.telegram_bot.service.ConsultantService;
//
//import javax.transaction.Transactional;
//
//@CrossOrigin
//@RestController
//
//@Transactional
//public class ConsultantController {
//    private final ConsultantService consultantService;

//    public ConsultantController(ConsultantService consultantService) {
//        this.consultantService = consultantService;
//    }
//    @PostMapping
//    public ResponseEntity<?> postMenu(@RequestBody ConsultantRegisterDto conRegDto) {

//        if (consultantService.saveConsultant(conRegDto)) {
//            return org.springframework.http.ResponseEntity.status(HttpStatus.CREATED).build();
//        } else {
//            return org.springframework.http.ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//}
//consultant.getFirstName(),consultant.getLastName(),consultant.getUserName(),consultant.getStatusWork(),consultant.getListClients()