package ru.eldar.service;


/**
 * The interface HelpService.
 */
public interface HelpService {

    /**
     * This method adds new support message
     *
     * @param message new support message
     */
    void addSupportMessage(String message);

    /**
     * This method gets a support message.
     *
     * @return a support message
     */
    String getSupportMessage();
}
