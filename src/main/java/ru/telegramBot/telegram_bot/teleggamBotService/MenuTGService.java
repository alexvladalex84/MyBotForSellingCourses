package ru.telegramBot.telegram_bot.teleggamBotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.telegramBot.telegram_bot.DTO.TextForCommandDto;
import ru.telegramBot.telegram_bot.entity.Menu;
import ru.telegramBot.telegram_bot.repository.MenuForBotRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * класс для создания меню в телеграм боте.
 * для изменения текста для команды
 */
@Service
public class MenuTGService {
    private final MenuForBotRepository menuForBotRepository;

    Logger LOG = LoggerFactory.getLogger(MenuTGService.class);

    public MenuTGService(MenuForBotRepository menuForBotRepository) {
        this.menuForBotRepository = menuForBotRepository;
    }

    /**
     * метод сохраняет командлы для меню телеграм бота ,
     * если введенная  команда уже существует то старая стирается а новая записывается
     *
     * @param pk
     * @param command
     * @param description
     * @return
     */
    public boolean saveCommandForBot(Integer pk, String command, String description, String text) {
        Menu newCommand = new Menu();

        Optional<Menu> m = menuForBotRepository.findByNameCommand(command);

        if (m.isEmpty()) {
            newCommand.setNameCommand(command);
            newCommand.setDescriptionCommand(description);
            newCommand.setText(text);
            menuForBotRepository.save(newCommand);
            return true;
        } else if (m.get().getNameCommand().equals(command)) {
            newCommand.setPk(m.get().getPk());
            newCommand.setNameCommand(command);
            newCommand.setDescriptionCommand(description);
            newCommand.setText(text);
            menuForBotRepository.save(newCommand);
            return true;
        }
        return false;
    }


    /**
     * метод возвращает список всех команд телеграм бота
     *
     * @return
     */
    public List<BotCommand> getListOfCommands() {
        List<Menu> commands = menuForBotRepository.findAll();
        List<BotCommand> listOfCommand = new ArrayList<>();
        if (commands.isEmpty()) {
            listOfCommand.add(new BotCommand("/error", "что-то пошло не так "));
            return listOfCommand;
        }


        for (Menu element : commands) {
            listOfCommand.add(new BotCommand(element.getNameCommand(), element.getDescriptionCommand()));
        }


        return listOfCommand;
    }

    public TextForCommandDto updateText(TextForCommandDto textForCommandDto) {

        Menu menu = null;
        try {
            menu = menuForBotRepository.findByNameCommand(textForCommandDto.getNameCommand()).get();
        } catch (NoSuchElementException e) {
            LOG.error(e.getMessage());
        }
        TextForCommandDto text = new TextForCommandDto();

        if (menu != null && menu.getNameCommand().equals(textForCommandDto.getNameCommand())) {
            menu.setText(textForCommandDto.getText());
            menuForBotRepository.save(menu);
            text.setNameCommand(menu.getNameCommand());
            text.setText(menu.getText());
            return text;
        }
        return null;
    }

    public String getTextOFCommand() {
        return null;
    }
}


