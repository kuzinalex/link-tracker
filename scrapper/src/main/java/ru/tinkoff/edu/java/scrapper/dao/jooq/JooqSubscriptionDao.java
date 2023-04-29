package ru.tinkoff.edu.java.scrapper.dao.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.SubscriptionRecord;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.SUBSCRIPTION;

//@Repository
@AllArgsConstructor
public class JooqSubscriptionDao implements SubscriptionDao {

	private final DSLContext dslContext;

	@Override
	public int add(Long tgChatId, Long linkId) {

		return dslContext.insertInto(SUBSCRIPTION, SUBSCRIPTION.CHAT_ID, SUBSCRIPTION.LINK_ID)
				.values(tgChatId, linkId)
				.execute();
	}

	@Override
	public int remove(Long tgChatId, Long linkId) {

		return dslContext.delete(SUBSCRIPTION)
				.where(SUBSCRIPTION.CHAT_ID.eq(tgChatId), SUBSCRIPTION.LINK_ID.eq(linkId))
				.execute();
	}

	@Override
	public Subscription find(Long tgChatId, Long linkId) {

		return dslContext.select(SUBSCRIPTION)
				.from(SUBSCRIPTION)
				.where(SUBSCRIPTION.CHAT_ID.eq(tgChatId), SUBSCRIPTION.LINK_ID.eq(linkId))
				.fetch()
				.map(record -> {
					SubscriptionRecord subscriptionRecord = (SubscriptionRecord) record.getValue(0);
					return new Subscription(subscriptionRecord.getValue(SUBSCRIPTION.CHAT_ID),
							subscriptionRecord.getValue(SUBSCRIPTION.LINK_ID));
				}).stream().findFirst().orElse(null);
	}
}
