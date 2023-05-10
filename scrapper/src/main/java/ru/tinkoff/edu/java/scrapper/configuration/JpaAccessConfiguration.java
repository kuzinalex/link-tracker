package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaSubscriptionService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jpa")
@AllArgsConstructor
public class JpaAccessConfiguration {

	private final JpaChatRepository chatRepository;
	private final JpaLinkRepository linkRepository;
	private final JpaSubscriptionRepository subscriptionRepository;

	@Bean
	JpaChatDao chatDao(){
		return new JpaChatDao(chatRepository);
	}

	@Bean
	JpaLinkDao linkDao(){
		return new JpaLinkDao(linkRepository,subscriptionRepository);
	}

	@Bean
	JpaSubscriptionDao subscriptionDao(){
		return new JpaSubscriptionDao(subscriptionRepository,chatRepository);
	}

	@Bean
	public ChatService chatService() {
		return new JpaChatService(chatDao());
	}

	@Bean
	public SubscriptionService subscriptionService(){
		return new JpaSubscriptionService(linkDao(),subscriptionDao());
	}

	@Bean
	public LinkService linkService(){
		return new JpaLinkService(linkDao());
	}

}
