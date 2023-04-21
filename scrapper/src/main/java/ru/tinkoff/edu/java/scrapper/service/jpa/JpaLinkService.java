package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
public class JpaLinkService implements LinkService {

	private final LinkDao linkDao;

	@Override
	public List<Link> findOld(OffsetDateTime checkTime) {

		return linkDao.findOld(checkTime);
	}

	@Override
	public int update(Link link) {

		linkDao.update(link);
		return 0;
	}
}
