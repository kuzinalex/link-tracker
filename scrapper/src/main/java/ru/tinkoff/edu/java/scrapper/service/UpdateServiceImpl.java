package ru.tinkoff.edu.java.scrapper.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.dto.GitHubLinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowLinkParserDTO;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.StackOverflowClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UpdateServiceImpl implements UpdateService {

	private final LinkDao linkDao;
	private final ChatDao chatDao;
	private final LinkParser linkParser;
	private final GitHubClient gitHubClient;
	private final StackOverflowClient stackOverflowClient;
	private final BotClient botClient;
	private final ApplicationProperties properties;

	@Override
	public int update() throws MalformedURLException {

		List<Link> links = linkDao.findOld(OffsetDateTime.now().minusMinutes(properties.oldLinkInterval()));
		ParserResponse<LinkParserDTO> response;
		for (Link link : links) {
			response = linkParser.parse(new URL(link.getUrl()));
			checkUpdates(response, link);
		}
		return 0;
	}

	private void checkUpdates(ParserResponse<LinkParserDTO> response, Link link) {

		if (response.response() instanceof GitHubLinkParserDTO) {
			checkGutHub(response, link);
		} else if (response.response() instanceof StackOverflowLinkParserDTO) {
			checkStackOverflowResponse(response, link);
		}
	}

	private void checkStackOverflowResponse(ParserResponse<LinkParserDTO> response, Link link) {

		String questionId = ((StackOverflowLinkParserDTO) response.response()).id();
		StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(questionId).block();
		if (link.getUpdatedAt().isBefore(stackOverflowResponse.items()[0].last_edit_date())) {
			List<Long> ids = chatDao.findByLink(link.getId());
			link.setUpdatedAt(stackOverflowResponse.items()[0].last_edit_date());
			linkDao.update(link);
			botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl(), "", ids.toArray(Long[]::new))).block();
		}
	}

	private void checkGutHub(ParserResponse<LinkParserDTO> response, Link link) {

		String repoName = ((GitHubLinkParserDTO) response.response()).repoName();
		String username = ((GitHubLinkParserDTO) response.response()).username();
		GitHubResponse gitHubResponse = gitHubClient.fetchRepository(username, repoName).block();
		if (link.getUpdatedAt().isBefore(gitHubResponse.updated_at())) {
			List<Long> ids = chatDao.findByLink(link.getId());
			link.setUpdatedAt(gitHubResponse.updated_at());
			linkDao.update(link);
			botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl(), "", ids.toArray(Long[]::new))).block();
		}
	}
}
