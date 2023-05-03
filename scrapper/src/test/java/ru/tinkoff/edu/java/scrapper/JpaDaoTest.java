package ru.tinkoff.edu.java.scrapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ScrapperApplication.class, IntegrationEnvironment.IntegrationEnvironmentConfiguration.class})
public class JpaDaoTest extends IntegrationEnvironment {

	@Autowired
	private JpaChatDao chatDao;
	@Autowired
	private JpaLinkDao linkDao;
	@Autowired
	private JpaSubscriptionDao subscriptionDao;

	@DynamicPropertySource
	static void jpaProperties(DynamicPropertyRegistry registry) {

		registry.add("app.databaseAccessType", () -> "jpa");
	}

	@Test
	@Transactional
	public void addAndFindLinkTest() {

		long linkId = linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));
		Link link = linkDao.find(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));

		assertThat(linkId).isEqualTo(link.getId());
	}

	@Test
	@Transactional
	public void addAndDeleteChatTest() throws DuplicateChatException {

		chatDao.add(1L);
		assertThat(chatDao.findAll()).isNotEmpty();

		chatDao.remove(1L);

		assertThat(chatDao.findAll()).isEmpty();
	}

	@Test
	@Transactional
	public void findAllChatTrackLinks() throws DuplicateChatException {

		chatDao.add(1L);
		long linkOneId = linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));
		long linkTwoId = linkDao.add(URI.create("https://github.com/kuzinalex/not-tinkoff-tracker"));
		subscriptionDao.add(1L, linkOneId);
		subscriptionDao.add(1L, linkTwoId);

		assertThat(linkDao.findAll(1L)).hasSize(2);
	}

	@Test
	@Transactional
	public void trackAndUntrackLinkTest() throws DuplicateChatException {

		chatDao.add(1L);
		chatDao.add(2L);
		long linkId = linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));
		subscriptionDao.add(1L, linkId);
		subscriptionDao.add(2L, linkId);

		assertThat(chatDao.findLinkSubscribers(linkId)).hasSize(2);

		subscriptionDao.remove(1L, linkId);

		assertThat(chatDao.findLinkSubscribers(linkId)).hasSize(1);
	}

	@Test
	@Transactional
	public void updateLinkTest() {

		long linkId = linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));
		Link link = new Link(linkId, "https://github.com/kuzinalex/tinkoff-tracker", OffsetDateTime.now().plusHours(1L), OffsetDateTime.now().plusHours(2L));
		linkDao.update(link);
		Link updatedLink = linkDao.find(URI.create(link.getUrl()));

		assertThat(link).isNotEqualTo(updatedLink);
	}

	@Test
	@Transactional
	public void findOldLinksTest() {

		linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));

		assertThat(linkDao.findOld(OffsetDateTime.now().plusHours(2))).hasSize(1);
	}
}
