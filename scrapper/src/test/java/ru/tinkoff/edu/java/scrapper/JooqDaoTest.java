package ru.tinkoff.edu.java.scrapper;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Random;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ScrapperApplication.class, IntegrationEnvironment.IntegrationEnvironmentConfiguration.class})
public class JooqDaoTest extends IntegrationEnvironment {

    public static final String TEST_URI_ONE = "https://github.com/kuzinalex/tinkoff";
    public static final String TEST_URI_TWO = "https://github.com/kuzinalex/parser";
    @Autowired
    private JooqChatDao chatDao;
    @Autowired
    private JooqLinkDao linkDao;
    @Autowired
    private JooqSubscriptionDao subscriptionDao;

    @DynamicPropertySource
    static void jpaProperties(DynamicPropertyRegistry registry) {

        registry.add("app.databaseAccessType", () -> "jooq");
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void addAndFindLinkTest() {

        long addedLinkId = linkDao.add(new URI(TEST_URI_ONE));

        Link findedLink = linkDao.find(new URI(TEST_URI_ONE));

        assertThat(addedLinkId).isEqualTo(findedLink.getId());
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void findAllLinksByChatTest() {

        long randomLong = new Random().nextLong();

        chatDao.add(randomLong);
        long linkOneId = linkDao.add(new URI("https://github.com/kuzinalex/tracker"));
        long linkTwoId = linkDao.add(new URI("https://github.com/kuzinalex/scrapper"));
        subscriptionDao.add(randomLong, linkOneId);
        subscriptionDao.add(randomLong, linkTwoId);

        assertThat(linkDao.findAll(randomLong)).hasSize(2);
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void subscriptionTest() {

        long randomLong = new Random().nextLong();
        long randomLong1 = new Random().nextLong();

        chatDao.add(randomLong);
        chatDao.add(randomLong1);
        long linkId = linkDao.add(new URI("https://github.com/kuzinalex/bot"));
        subscriptionDao.add(randomLong, linkId);
        subscriptionDao.add(randomLong1, linkId);

        assertThat(chatDao.findLinkSubscribers(linkId)).hasSize(2);

        subscriptionDao.remove(randomLong, linkId);

        assertThat(chatDao.findLinkSubscribers(linkId)).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void updateLinkTest() {

        long addedLink = linkDao.add(new URI(TEST_URI_TWO));
        Link link = new Link(addedLink, TEST_URI_TWO, OffsetDateTime.now().plusHours(1L), OffsetDateTime.now().plusHours(2L));
        linkDao.update(link);
        Link updatedLink = linkDao.find(new URI(link.getUrl()));

        assertThat(link).isNotEqualTo(updatedLink);
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void findOlderThanLinkTest() {

        linkDao.add(new URI("https://github.com/kuzinalex/link-parser"));
        assertThat(linkDao.findOld(OffsetDateTime.now().plusHours(2))).hasSizeGreaterThan(0);
    }

}
