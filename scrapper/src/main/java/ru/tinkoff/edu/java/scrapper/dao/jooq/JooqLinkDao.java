package ru.tinkoff.edu.java.scrapper.dao.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.LinkRecord;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.LINK;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Subscription.SUBSCRIPTION;

//@Repository
@AllArgsConstructor
public class JooqLinkDao implements LinkDao {

	private final DSLContext dslContext;

	@Override
	public long add(URI url) {

		return dslContext.insertInto(LINK, LINK.URL)
				.values(url.toString())
				.returningResult(LINK.ID, LINK.URL, LINK.CHECK_TIME, LINK.UPDATED_AT)
				.fetchOne()
				.map(record -> record.get(LINK.ID));
	}

	@Override
	public int update(Link link) {

		return dslContext.update(LINK)
				.set(LINK.UPDATED_AT, link.getUpdatedAt().toLocalDateTime())
				.set(LINK.CHECK_TIME, OffsetDateTime.now().toLocalDateTime())
				.where(LINK.ID.eq(link.getId()))
				.execute();
	}

	@Override
	public int remove(Long link) {

		return dslContext.delete(LINK)
				.where(LINK.ID.eq(link))
				.execute();
	}

	@Override
	public Link find(URI url) {

		return dslContext.select(LINK)
				.from(LINK)
				.where(LINK.URL.eq(url.toString()))
				.fetch()
				.map(record -> {
					LinkRecord linkRecord = (LinkRecord) record.get(0);
					return new Link(
							linkRecord.getValue(LINK.ID), linkRecord.getValue(LINK.URL),
							OffsetDateTime.of(linkRecord.getValue(LINK.CHECK_TIME), ZoneOffset.UTC),
							OffsetDateTime.of(linkRecord.getValue(LINK.UPDATED_AT), ZoneOffset.UTC));
				}).stream().findFirst().orElse(null);
	}

	@Override
	public List<Link> findAll(Long tgChatId) {

		return dslContext.select(LINK)
				.from(LINK)
				.join(SUBSCRIPTION).on(LINK.ID.eq(SUBSCRIPTION.LINK_ID))
				.where(SUBSCRIPTION.CHAT_ID.eq(tgChatId))
				.fetch()
				.map(record -> {
					LinkRecord linkRecord = (LinkRecord) record.get(0);
					return new Link(
							linkRecord.getValue(LINK.ID), linkRecord.getValue(LINK.URL),
							OffsetDateTime.of(linkRecord.getValue(LINK.CHECK_TIME), ZoneOffset.UTC),
							OffsetDateTime.of(linkRecord.getValue(LINK.UPDATED_AT), ZoneOffset.UTC));
				});
	}

	@Override
	public List<Link> findOld(OffsetDateTime checkTime) {

		return dslContext.select(LINK)
				.from(LINK)
				.where(LINK.CHECK_TIME.lessOrEqual(checkTime.toLocalDateTime()))
				.fetch()
				.map(record -> {
					LinkRecord linkRecord = (LinkRecord) record.get(0);
					return new Link(
							linkRecord.getValue(LINK.ID), linkRecord.getValue(LINK.URL),
							OffsetDateTime.of(linkRecord.getValue(LINK.CHECK_TIME), ZoneOffset.UTC),
							OffsetDateTime.of(linkRecord.getValue(LINK.UPDATED_AT), ZoneOffset.UTC));
				});
	}
}
