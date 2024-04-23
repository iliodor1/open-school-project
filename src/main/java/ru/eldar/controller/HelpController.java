package ru.eldar.controller;

import ru.eldar.annotation.Controller;
import ru.eldar.annotation.RequestMapping;
import ru.eldar.constant.RequestMethod;
import ru.eldar.dto.MessageDto;
import ru.eldar.service.HelpService;

@Controller(path = "/help/v1")
public class HelpController {
    private final HelpService helpService;

    public HelpController(HelpService helpService) {
        this.helpService = helpService;
    }

    @RequestMapping(path = "/support", method = RequestMethod.POST)
    public void addMessage(MessageDto messageDto) {
        helpService.addSupportMessage(messageDto.getMessage());
    }

    @RequestMapping(path = "/support", method = RequestMethod.GET)
    public MessageDto getMessage() {
        var supportMessage = helpService.getSupportMessage();

        return MessageDto.builder()
                         .message(supportMessage)
                         .build();
    }
}
