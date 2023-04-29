package ru.tinkoff.edu.java.scrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.service.UpdateService;
import ru.tinkoff.edu.java.scrapper.service.UpdateServiceImpl;
import ru.tinkoff.edu.java.scrapper.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableScheduling
public class ScrapperApplication {

	public static void main(String[] args) throws JSONException, JsonProcessingException, URISyntaxException, MalformedURLException {

		var ctx = SpringApplication.run(ScrapperApplication.class, args);


		// быстрый тест работоспособности
		GitHubClient gitHubClient = (GitHubClient) ctx.getBean("gitHubClient");

		StackOverflowClient overflowClient = (StackOverflowClient) ctx.getBean("stackOverflowClient");

		GitHubResponse data = gitHubClient.fetchRepository("kuzinalex", "tinkoff-tracker").block();
		System.out.println(data);

		StackOverflowResponse soData = overflowClient.fetchQuestion("348170").block();
		System.out.println(soData);

		JdbcLinkDao dao= (JdbcLinkDao) ctx.getBean("jdbcLinkDao");
		JdbcChatDao chatDao= (JdbcChatDao) ctx.getBean("jdbcChatDao");
//		chatDao.add(2L);

		UpdateService updateService = (UpdateService) ctx.getBean(UpdateServiceImpl.class);
		updateService.update();
	}
}
