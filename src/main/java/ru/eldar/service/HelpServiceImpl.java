package ru.eldar.service;

import java.util.List;
import java.util.Random;
import ru.eldar.dao.HelpMessageDao;
import ru.eldar.dao.HelpMessageMemoryDao;

public class HelpServiceImpl implements HelpService {
    private static HelpService instance;

    private final HelpMessageDao helpMessageDao = HelpMessageMemoryDao.getInstance();

    private static final String DEFAULT_MESSAGE = "You are amazing and you know it!";

    private final Random rnd = new Random();

    private HelpServiceImpl() {
    }

    public static HelpService getInstance() {
        if (instance == null) {
            instance = new HelpServiceImpl();
        }
        return instance;
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
