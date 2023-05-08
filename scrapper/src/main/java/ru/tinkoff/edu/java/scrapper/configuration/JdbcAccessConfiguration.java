package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcSubscriptionService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jdbc")
@AllArgsConstructor
public class JdbcAccessConfiguration {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    JdbcChatDao chatDao() {

        return new JdbcChatDao(jdbcTemplate);
    }

    @Bean
    JdbcLinkDao linkDao() {

        return new JdbcLinkDao(jdbcTemplate);
    }

    @Bean
    JdbcSubscriptionDao subscriptionDao() {

        return new JdbcSubscriptionDao(jdbcTemplate);
    }

    @Bean
    public ChatService chatService() {

        return new JdbcChatService(chatDao());
    }

    @Bean
    public SubscriptionService subscriptionService() {

        return new JdbcSubscriptionService(linkDao(), subscriptionDao());
    }

    @Bean
    public LinkService linkService() {

        return new JdbcLinkService(linkDao());
    }

}
