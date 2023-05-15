package ru.tinkoff.edu.java.scrapper.dao.jpa;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.repository.JpaSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.jpa.LinkEntity;
import ru.tinkoff.edu.java.scrapper.entity.jpa.SubscriptionEntity;

@AllArgsConstructor
public class JpaLinkDao implements LinkDao {

    private final JpaLinkRepository linkRepository;
    private final JpaSubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public long add(URI url) {

        LinkEntity link = new LinkEntity();
        link.setUrl(url.toString());
        link.setCheckTime(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());
        link.setSubscription(new HashSet<>());
        LinkEntity saved = linkRepository.save(link);
        return saved.getId();
    }

    @Override
    public int update(Link link) {

        Set<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findAllByLinkId(link.getId());
        LinkEntity linkEntity = linkToEntity(link, subscriptionEntities);

        LinkEntity updated = linkRepository.save(linkEntity);
        return Math.toIntExact(updated.getId());
    }

    @Override
    public int remove(Long link) {

        linkRepository.deleteById(link);
        return 1;
    }

    @Override
    public Link find(URI url) {

        LinkEntity entity = linkRepository.findByUrl(url.toString());
        if (entity != null) {
            return entityToLink(entity);
        } else {
            return null;
        }
    }

    @Override
    public List<Link> findAll(Long tgChatId) {

        return linkRepository.findAllByTgChat(tgChatId).stream().map(this::entityToLink).toList();
    }

    @Override
    public List<Link> findOld(OffsetDateTime checkTime) {

        return linkRepository.findAllByCheckTimeBefore(checkTime)
            .stream().map(this::entityToLink).toList();
    }

    private LinkEntity linkToEntity(Link link, Set<SubscriptionEntity> subscriptionEntitySet) {

        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setId(link.getId());
        linkEntity.setUrl(link.getUrl());
        linkEntity.setUpdatedAt(link.getUpdatedAt());
        linkEntity.setCheckTime(OffsetDateTime.now());
        linkEntity.setSubscription(subscriptionEntitySet);
        return linkEntity;
    }

    private Link entityToLink(LinkEntity linkEntity) {

        return new Link(linkEntity.getId(), linkEntity.getUrl(), linkEntity.getCheckTime(), linkEntity.getUpdatedAt());
    }
}
