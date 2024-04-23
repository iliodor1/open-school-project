package ru.eldar.service;

import java.util.List;
import java.util.Random;
import ru.eldar.dao.HelpMessageDao;

public class HelpServiceImpl implements HelpService {

    private final HelpMessageDao helpMessageDao;

    private final Random rnd = new Random();

    private static final String DEFAULT_MESSAGE = "You are amazing and you know it!";


    public HelpServiceImpl(HelpMessageDao helpMessageDao) {
        this.helpMessageDao = helpMessageDao;
    }


    @Override
    public void addSupportMessage(String message) {
        helpMessageDao.addMessage(message);
    }

    @Override
    public String getSupportMessage() {
        List<String> messages = helpMessageDao.getAll();
        if (messages.isEmpty()) {
            return DEFAULT_MESSAGE;
        }

        var number = rnd.nextInt(messages.size());

        return messages.get(number);
    }
}
