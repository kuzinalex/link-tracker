package ru.tinkoff.edu.java.scrapper.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.dto.GitHubLinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowLinkParserDTO;
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
public class LinkUpdaterImpl implements LinkUpdater {

	private final LinkDao linkDao;
	private final ChatDao chatDao;
	private final LinkParser linkParser;
	private final GitHubClient gitHubClient;
	private final StackOverflowClient stackOverflowClient;
	private final BotClient botClient;

	@Override
	public int update() throws MalformedURLException {

		List<Link> links = linkDao.findOld(OffsetDateTime.now().minusHours(1L));
		ParserResponse<LinkParserDTO> response;
		for (Link link : links) {
			response = linkParser.parse(new URL(link.getUrl()));
			if (response.response() instanceof GitHubLinkParserDTO) {
				String repoName = ((GitHubLinkParserDTO) response.response()).repoName();
				String username = ((GitHubLinkParserDTO) response.response()).username();
				GitHubResponse gitHubResponse = gitHubClient.fetchRepository(username, repoName).block();
//				if (link.getUpdatedAt().isBefore(gitHubResponse.updated_at())) {
					List<Long> ids = chatDao.findByLink(link.getId());
					var a = botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl(), "", ids.toArray(Long[]::new)));
//				}
			}else if (response.response() instanceof StackOverflowLinkParserDTO){
				String questionId=((StackOverflowLinkParserDTO) response.response()).id();
				StackOverflowResponse stackOverflowResponse=stackOverflowClient.fetchQuestion(questionId).block();
				if (link.getUpdatedAt().isBefore(stackOverflowResponse.items()[0].last_edit_date())){
					List<Long> ids = chatDao.findByLink(link.getId());
					var a = botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl(), "", ids.toArray(Long[]::new)));

				}
			}

		}

		return 0;
	}
}
