package ru.eldar.service;


import ru.eldar.annotation.Logging;

/**
 * The interface HelpService.
 */
public interface HelpService {

    /**
     * This method adds new support message
     *
     * @param message new support message
     */
    @Logging(
            before = "Received a request to add a support message",
            after = "The request to add a message was processed successfully"
    )
    void addSupportMessage(String message);

    /**
     * This method gets a support message.
     *
     * @return a support message
     */
    @Logging(
            before = "Received a request to get a support message",
            after = "The request to get a message was processed successfully"
    )
    String getSupportMessage();
}
