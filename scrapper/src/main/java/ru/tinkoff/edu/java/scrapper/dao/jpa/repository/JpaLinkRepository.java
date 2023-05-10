package ru.tinkoff.edu.java.scrapper.dao.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.entity.jpa.LinkEntity;

import java.time.OffsetDateTime;
import java.util.List;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {

	LinkEntity findByUrl(String url);

	List<LinkEntity> findAllByCheckTimeBefore(OffsetDateTime checkTime);

	@Query("select l from LinkEntity l join SubscriptionEntity s where s.linkId=l.id and s.chatId=:tgChatId")
	List<LinkEntity> findAllByTgChat(@Param("tgChatId") Long tgChatId);
}
