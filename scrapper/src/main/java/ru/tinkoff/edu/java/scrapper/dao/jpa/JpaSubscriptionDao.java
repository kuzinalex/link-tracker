package ru.tinkoff.edu.java.scrapper.dao.jpa;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;
import ru.tinkoff.edu.java.scrapper.entity.jpa.ChatEntity;
import ru.tinkoff.edu.java.scrapper.entity.jpa.SubscriptionEntity;

@AllArgsConstructor
public class JpaSubscriptionDao implements SubscriptionDao {

    private final JpaSubscriptionRepository subscriptionRepository;
    private final JpaChatRepository chatRepository;

    @Override
    public int add(Long tgChatId, Long linkId) {
        if (subscriptionRepository.findByChatIdAndLinkId(tgChatId, linkId) != null) {
            return 0;
        }
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(tgChatId, linkId);
        ChatEntity chatEntity = chatRepository.getById(tgChatId);
        chatEntity.getSubscription().add(subscriptionEntity);
        chatRepository.save(chatEntity);
        return 1;
    }

    @Override
    public int remove(Long tgChatId, Long linkId) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByChatIdAndLinkId(tgChatId, linkId);
        if (subscriptionEntity == null) {
            return 0;
        }
        ChatEntity chatEntity = chatRepository.getById(tgChatId);
        chatEntity.getSubscription().remove(subscriptionEntity);
        chatRepository.save(chatEntity);
        return 1;
    }

    @Override
    public Subscription find(Long tgChatId, Long linkId) {

        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByChatIdAndLinkId(tgChatId, linkId);
        return entityToSubscription(subscriptionEntity);
    }

    private Subscription entityToSubscription(SubscriptionEntity entity) {
        Subscription subscription = new Subscription();
        subscription.setChatId(entity.getChatId());
        subscription.setLinkId(entity.getLinkId());
        return subscription;
    }
}
