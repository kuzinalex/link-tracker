package ru.tinkoff.edu.java.scrapper.service.update;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.linkparser.dto.GitHubLinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowLinkParserDTO;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEventType;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.StackOverflowClient;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class UpdateService {

	private final LinkService linkService;
	private final ChatService chatService;
	private final GitHubClient gitHubClient;
	private final StackOverflowClient stackOverflowClient;
	private final BotClient botClient;
	private final ApplicationProperties properties;

	public List<Link> findOld(OffsetDateTime checkTime) {

		return linkService.findOld(OffsetDateTime.now().minusMinutes(properties.oldLinkInterval()));
	}

	public void checkUpdates(ParserResponse<LinkParserDTO> response, Link link) {

		switch (response.response()) {
		case GitHubLinkParserDTO dto -> checkGitHub(dto, link);
		case StackOverflowLinkParserDTO dto -> checkStackOverflow(dto, link);
		}
	}

	private void checkStackOverflow(StackOverflowLinkParserDTO response, Link link) {

		String questionId = response.id();
		StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(questionId).block();
		if (link.getUpdatedAt().isBefore(stackOverflowResponse.items()[0].lastEditDate())) {
			List<Long> ids = chatDao.findLinkSubscribers(link.getId());
			botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl(), "", ids.toArray(Long[]::new))).block();

			link.setUpdatedAt(stackOverflowResponse.items()[0].lastEditDate());
		}
		linkDao.update(link);
	}

	private void checkGitHub(GitHubLinkParserDTO response, Link link) {

		String username = response.username();
		String repo = response.repoName();

		GitHubResponse gitHubResponse = gitHubClient.fetchRepository(username, repo).block();
		OffsetDateTime lastRepoUpdate = (gitHubResponse.pushedAt().isAfter(gitHubResponse.updatedAt())) ? gitHubResponse.pushedAt() : gitHubResponse.updatedAt();
		lastRepoUpdate = toLocalOffsetDateTime(lastRepoUpdate);

		if (link.getUpdatedAt().isBefore(lastRepoUpdate)) {

			List<Long> ids = chatDao.findLinkSubscribers(link.getId());
			String updateDescription = generateUpdateDescription(username, repo, link);
			botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl(), updateDescription, ids.toArray(Long[]::new))).block();

			link.setUpdatedAt(lastRepoUpdate);
		}
		linkService.update(link);
	}

	private String generateUpdateDescription(String username, String repoName, Link link) {

		StringBuilder stringBuilder = new StringBuilder();
		List<GitHubEvent> eventList = gitHubClient.fetchRepositoryEvents(username, repoName).collectList().block()
				.stream()
				.filter(gitHubEvent -> toLocalOffsetDateTime(gitHubEvent.createdAt()).isAfter(link.getUpdatedAt()))
				.toList();

		stringBuilder.append(checkPushEvents(eventList));
		stringBuilder.append(checkPullRequestEvents(eventList));

		return stringBuilder.toString();
	}

	private String checkPushEvents(List<GitHubEvent> eventList) {

		StringBuilder stringBuilder = new StringBuilder();
		List<GitHubEvent> pushEvents = eventList.stream()
				.filter(gitHubEvent -> gitHubEvent.type().equals(GitHubEventType.PUSH_EVENT.getValue()))
				.toList();

		if (!pushEvents.isEmpty()) {
			stringBuilder.append("Новые коммиты: ");
			for (GitHubEvent gitHubEvent : pushEvents) {
				for (GitHubEvent.Payload.Commit commit : gitHubEvent.payload().commits()) {
					stringBuilder
							.append("\n")
							.append(commit.sha())
							.append(" -- ")
							.append(commit.message())
							.append("\n");
				}
			}
		}
		return stringBuilder.toString();
	}

	private String checkPullRequestEvents(List<GitHubEvent> eventList) {

		StringBuilder stringBuilder = new StringBuilder();
		List<GitHubEvent> pullRequestEvents = eventList.stream()
				.filter(gitHubEvent -> gitHubEvent.type().equals(GitHubEventType.PULL_REQUEST_EVENT.getValue()))
				.toList();

		if (!pullRequestEvents.isEmpty()) {
			stringBuilder.append("Новые пулл реквесты: ");
			for (GitHubEvent gitHubEvent : pullRequestEvents) {
				stringBuilder
						.append("\n")
						.append(gitHubEvent.payload().pullRequest().title())
						.append(" -- ")
						.append(gitHubEvent.payload().pullRequest().url())
						.append("\n");

			}
		}
		return stringBuilder.toString();
	}

	private OffsetDateTime toLocalOffsetDateTime(OffsetDateTime offsetDateTime) {

		return offsetDateTime.plusNanos((TimeZone.getDefault().getRawOffset() * 1_000_000L));
	}
}
