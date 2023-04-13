package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Subscription;

import java.util.List;

public interface SubscriptionDao {

	int add(Long tgChatId, Long linkId);
	int remove(Long tgChatId, Long linkId);
	List<Subscription> findAll(Long tgChatId);
	Subscription find(Long tgChatId, Long linkId);

}
