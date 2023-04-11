package ru.tinkoff.edu.java.scrapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfiguration.class)
public class JdbcDaoTest extends IntegrationEnvironment {

	@Autowired
	private JdbcChatDao chatDao;
	@Autowired
	private JdbcLinkDao linkDao;

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
		linkDao.addSubscription(1L, linkOneId);
		linkDao.addSubscription(1L, linkTwoId);

		assertThat(linkDao.findAll(1L)).hasSize(2);
	}

	@Test
	@Transactional
	public void trackAndUntrackLinkTest() throws DuplicateChatException {

		chatDao.add(1L);
		chatDao.add(2L);
		long linkId = linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));
		linkDao.addSubscription(1L, linkId);
		linkDao.addSubscription(2L, linkId);

		assertThat(chatDao.findByLink(linkId)).hasSize(2);

		linkDao.remove(1L, linkId);

		assertThat(chatDao.findByLink(linkId)).hasSize(1);
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
	public void findOldTEst() {

		linkDao.add(URI.create("https://github.com/kuzinalex/tinkoff-tracker"));

		assertThat(linkDao.findOld(OffsetDateTime.now().plusHours(2))).hasSize(1);
	}

}
