package ru.telegramBot.telegram_bot.controller;

import javax.transaction.Transactional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.telegramBot.telegram_bot.DTO.TextForCommandDto;
import ru.telegramBot.telegram_bot.entity.Menu;
import ru.telegramBot.telegram_bot.teleggamBotService.MenuTGService;

@CrossOrigin
@RestController
@RequestMapping(path = "/menuCommand")
@Transactional

public class MenuController {
    private final MenuTGService menuTGService;

    public MenuController(MenuTGService menuTGService) {
        this.menuTGService = menuTGService;
    }

    @PostMapping
    public ResponseEntity<?> postMenu(@RequestBody Menu menu) {
        if (menuTGService.saveCommandForBot(menu.getPk(),menu.getNameCommand(), menu.getDescriptionCommand(),menu.getText())) {
            return org.springframework.http.ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return org.springframework.http.ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PatchMapping(path = "/text")
    public ResponseEntity<TextForCommandDto> postTextForCommand(@RequestBody TextForCommandDto textForCommandDto) {
       TextForCommandDto text = menuTGService.updateText(textForCommandDto);

            if (text != null) {
                return ResponseEntity.ok(text);
            } else {
                return ResponseEntity.status(404).build();
            }
    }
}
