package ru.tinkoff.edu.java.scrapper.dao.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.entity.jpa.SubscriptionEntity;

import java.util.List;
import java.util.Set;

public interface JpaSubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	SubscriptionEntity findByChatIdAndLinkId(Long chatId, Long linkId);

	Set<SubscriptionEntity> findAllByLinkId(Long linkId);
}

