package ru.eldar.dao;

import java.util.List;

/**
 * The interface for interacting with message storage/
 */
public interface HelpMessageDao {

    /**
     * This method gets all messages from storage
     *
     * @return the all messages
     */
    List<String> getAll();

    /**
     * Add message to storage
     *
     * @param message the new message
     */
    void addMessage(String message);
}
