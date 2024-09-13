package ru.telegramBot.telegram_bot.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.telegramBot.telegram_bot.entity.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * класс для сохранения получения и удаления клиента при регестрации!Сохранение данных в лист для последующего сохранения  в базу данных
 */
@Service
public class ClientListRepositoryForRegister {
    private List<Client> clientList = new ArrayList<>();
    private Map<Long, Client> clientMap = new HashMap<>();
    Logger LOG = LoggerFactory.getLogger(ClientListRepositoryForRegister.class);



    public void saveClientMap(Client client) {
        boolean key = clientMap.containsKey(client.getChatId());
        Client cl = clientMap.get(client.getChatId());

        if (key == false) {
            clientMap.put(client.getChatId(), client);


        }
        try {
            if (cl.getFirstName() == null && client.getFirstName() != null) {
                cl.setFirstName(client.getFirstName());
                clientMap.put(cl.getChatId(), cl);
                LOG.info("if 1 save");
            } else if (cl.getFirstName() != null && cl.getLastName() == null && client.getLastName() != null) {
                cl.setLastName(client.getLastName());
                clientMap.put(cl.getChatId(), cl);
                LOG.info("if 1 save");
            } else if (cl.getFirstName() != null && cl.getLastName() != null && cl.getNumberPhone() == null && client.getNumberPhone() != null) {
                cl.setNumberPhone(client.getNumberPhone());
                clientMap.put(cl.getChatId(), cl);
                LOG.info("if 1 save");
            }
        } catch (NullPointerException e) {

        }

    }

    public Client getClientRegisterMap(Long chatId) throws NullPointerException {
        Client client = new Client();
        Client cl = clientMap.get(chatId);
        boolean key = clientMap.containsKey(chatId);
        if (key) {
            if (cl.getFirstName() == null) {
                LOG.info("if 1 get");
                return cl;
            } else if (cl.getFirstName() != null && cl.getLastName() == null) {
                LOG.info("if 2 get");
                return cl;
            } else if (cl.getFirstName() != null && cl.getLastName() != null && cl.getNumberPhone() == null) {
                LOG.info("if 3 get");
                return cl;
            }
        }
        return client;
    }

    public void deleteClientMap(Long chatId) {

        clientMap.remove(chatId);
        LOG.info("delete");


    }
    public void saveClientInList(Client client) throws NullPointerException {
        if (clientList.isEmpty()) {
            clientList.add(client);
            LOG.info("if 1 save");
        } else if (clientList.contains(client) && client.getFirstName() != null) {
            clientList.add(client);
            LOG.info("if 2 save");
        } else if (clientList.contains(client) && client.getLastName() != null) {
            clientList.add(client);
            LOG.info("if 3 save");
        }


    }
    public Client getClientRegister() throws NullPointerException {
        Client client = new Client();
        for (Client cl : clientList) {
            if (cl.getFirstName() == null) {
                LOG.info("if 1 get");
                return cl;
            } else if (cl.getFirstName() != null && cl.getLastName() == null) {
                LOG.info("if 2 get");
                return cl;
            } else if (cl.getFirstName() != null && cl.getLastName() != null && cl.getNumberPhone() == null) {
                LOG.info("if 3 get");
                return cl;
            }

        }
        LOG.info("else get");
        return client;
    }

    public void deleteListClient() {
        clientList.remove(0);
        LOG.info("delete 0");
        clientList.remove(1);
        LOG.info("delete 1");
        clientList.remove(0);
        LOG.info("delete 2");

    }
}
