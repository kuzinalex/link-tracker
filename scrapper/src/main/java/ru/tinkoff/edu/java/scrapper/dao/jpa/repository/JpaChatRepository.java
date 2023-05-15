package ru.tinkoff.edu.java.scrapper.dao.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.entity.jpa.ChatEntity;

public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("select c.id from ChatEntity c join SubscriptionEntity s where s.chatId=c.id and s.linkId=:linkId")
    List<Long> findAllByLink(@Param("linkId") Long linkId);
}
