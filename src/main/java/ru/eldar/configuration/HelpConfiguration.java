package ru.eldar.configuration;

import ru.eldar.annotation.Configuration;
import ru.eldar.annotation.Bean;
import ru.eldar.controller.HelpController;
import ru.eldar.dao.HelpMessageDao;
import ru.eldar.dao.HelpMessageMemoryDao;
import ru.eldar.requestmapping.JsonHandlerAdapter;
import ru.eldar.requestmapping.HandlerAdapter;
import ru.eldar.proxy.LoggingProxyApplier;
import ru.eldar.proxy.ProxyApplier;
import ru.eldar.mapper.JsonMapper;
import ru.eldar.service.HelpService;
import ru.eldar.service.HelpServiceImpl;

@Configuration
public class HelpConfiguration {

    @Bean
    public HelpService getHelpService(HelpMessageDao helpMessageDao) {
        return new HelpServiceImpl(helpMessageDao);
    }

    @Bean
    public HelpMessageDao getHelpMessageDao() {
        return new HelpMessageMemoryDao();
    }

    @Bean
    public HelpController getHelpController(HelpService helpService) {
        return new HelpController(helpService);
    }

    @Bean
    public HandlerAdapter getHandlerAdapter(JsonMapper jsonMapper) {
        return new JsonHandlerAdapter(jsonMapper);
    }

    @Bean
    public JsonMapper getJsonMapper() {
        return new JsonMapper();
    }

    @Bean
    public ProxyApplier getProxyApplier() {
        return new LoggingProxyApplier();
    }
}
