package ru.tinkoff.edu.java.scrapper.webclient.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.StackOverflowClient;

@Configuration
@AllArgsConstructor
public class ClientConfiguration {

	private static final String GITHUB_CLIENT = "gitHubClient";
	private static final String STACKOVERFLOW_CLIENT = "stackOverflowClient";

	private ApplicationConfig applicationConfig;

	@Bean(STACKOVERFLOW_CLIENT)
	public StackOverflowClient stackOverflowClient() {

		return StackOverflowClient.create(stackoverflowWebClient());
	}

	@Bean(GITHUB_CLIENT)
	public GitHubClient gitHubClient() {

		return GitHubClient.create(githubWebClient());
	}

	@Bean
	public WebClient githubWebClient() {

		return WebClient.builder()
				.baseUrl(applicationConfig.githubBaseUrl())
				.build();

	}

	@Bean
	public WebClient stackoverflowWebClient() {

		return WebClient.builder()
				.baseUrl(applicationConfig.stackoverflowBaseUrl())
				.build();

	}

}