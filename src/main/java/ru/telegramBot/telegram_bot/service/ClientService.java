package ru.telegramBot.telegram_bot.service;

import org.springframework.stereotype.Service;
import ru.telegramBot.telegram_bot.entity.Client;
import ru.telegramBot.telegram_bot.repository.ClientRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {
    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    public Client getByUserName(String userName) {
        try {
            return clientRepository.findByUserName(userName).get();
        } catch (NoSuchElementException e) {
            Client client = new Client();
            return client;

        }
    }
    public Client getByChatId(Long chatId) {
        try {
            return clientRepository.findByChatId(chatId).get();
        } catch (NullPointerException | NoSuchElementException e) {
            Client client = new Client();
            return client;

        }
    }
    public List<Client> getAll() {
        try {
            return clientRepository.findAll();
        } catch (NoSuchElementException e) {

            return null;

        }
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }
}
