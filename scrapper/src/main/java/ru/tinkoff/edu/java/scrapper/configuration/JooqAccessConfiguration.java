package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqSubscriptionService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jooq")
@AllArgsConstructor
public class JooqAccessConfiguration {

	private final DSLContext dslContext;

	@Bean
	JooqLinkDao linkDao() {

		return new JooqLinkDao(dslContext);
	}

	@Bean
	JooqChatDao chatDao() {

		return new JooqChatDao(dslContext);
	}

	@Bean
	JooqSubscriptionDao subscriptionDao() {

		return new JooqSubscriptionDao(dslContext);
	}

	@Bean
	public ChatService chatService() {

		return new JooqChatService(chatDao());
	}

	@Bean
	public SubscriptionService subscriptionService() {

		return new JooqSubscriptionService(linkDao(), subscriptionDao());
	}

	@Bean
	public LinkService linkService() {

		return new JooqLinkService(linkDao());
	}

}
