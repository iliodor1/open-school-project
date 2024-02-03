package ru.eldar.dao;

import java.util.List;

public interface HelpMessageDao {

    List<String> getAll();

    void addMessage(String message);
}
