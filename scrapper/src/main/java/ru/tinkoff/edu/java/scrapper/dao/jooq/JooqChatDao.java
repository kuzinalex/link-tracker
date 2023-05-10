package ru.tinkoff.edu.java.scrapper.dao.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.ChatRecord;

import java.util.List;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.CHAT;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.SUBSCRIPTION;

@AllArgsConstructor
public class JooqChatDao implements ChatDao {

	private final DSLContext dslContext;

	@Override
	public int add(Long tgChatId) throws DuplicateChatException {
		try {
			return dslContext.insertInto(CHAT, CHAT.ID)
					.values(tgChatId)
					.execute();
		}catch (Exception e){
			throw new DuplicateChatException(e.getMessage());
		}

	}

	@Override
	public int remove(Long tgChatId) {
		return dslContext.delete(CHAT)
				.where(CHAT.ID.eq(tgChatId))
				.execute();
	}

	@Override
	public List<Long> findLinkSubscribers(Long linkId) {
		return dslContext.select(CHAT)
				.from(CHAT)
				.join(SUBSCRIPTION).on(CHAT.ID.eq(SUBSCRIPTION.CHAT_ID))
				.where(SUBSCRIPTION.LINK_ID.eq(linkId))
				.fetch()
				.map(record -> {
					ChatRecord chatRecord = (ChatRecord) record.getValue(0);
					return chatRecord.getValue(CHAT.ID);
				});
	}

	@Override
	public List<Chat> findAll() {

		return dslContext.select(CHAT)
				.from(CHAT)
				.fetch().map(record -> {
					ChatRecord chatRecord = (ChatRecord) record.getValue(0);
					Chat chat=new Chat();
					chat.setId(chatRecord.getId());
					return chat;
				});
	}
}