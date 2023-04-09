package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
public class JdbcLinkDao implements LinkDao {

	public JdbcLinkDao(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}

	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public long add(URI url) {

		String insertQuery = "INSERT INTO link (url) VALUES (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, url.toString());
			return ps;
		}, keyHolder);

		return (long) keyHolder.getKeys().get("id");
	}

	@Transactional
	@Override
	public int addSubscription(Long tgChatId, Long linkId) throws SQLException {

		String insertQuery = "INSERT INTO subscription  VALUES (?,?)";

		return jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, tgChatId);
			ps.setLong(2, linkId);
			return ps;
		});

	}

	@Transactional
	@Override
	public int remove(Long tgChatId, Long link) {

		String query = "DELETE FROM subscription WHERE chat_id=(?) AND link_id=(?)";
		return jdbcTemplate.update(query, ps -> {
			ps.setLong(1, tgChatId);
			ps.setLong(2, link);
		});
	}

	@Override
	public Link find(URI url) {

		String query = "SELECT * FROM link WHERE url=(?)";
		return jdbcTemplate.query(query, ps -> {
			ps.setString(1, url.toString());
		}, linkRowMapper()).stream().findFirst().orElse(null);
	}

	@Transactional
	@Override
	public List<Link> findAll(Long tgChatId) {

		String query = "SELECT * FROM link JOIN subscription s on link.id = s.link_id WHERE chat_id=(?)";
		return jdbcTemplate.query(query, ps -> {
			ps.setLong(1, tgChatId);
		}, linkRowMapper());
	}

	@Transactional
	@Override
	public List<Link> findOld(OffsetDateTime checkTime) {

		String query = "SELECT * FROM link WHERE check_time<=(?)";
		return jdbcTemplate.query(query, ps -> {
			ps.setTimestamp(1, Timestamp.valueOf(checkTime.toLocalDateTime()));
		}, linkRowMapper());
	}

	private RowMapper<Link> linkRowMapper() {

		return (rs, rowNum) -> {
			Link link = new Link();
			link.setId(rs.getLong("id"));
			link.setUrl(rs.getString("url"));
			link.setCheckTime(OffsetDateTime.of(rs.getObject("check_time", LocalDateTime.class), ZoneOffset.UTC));
			link.setUpdatedAt(OffsetDateTime.of(rs.getObject("updated_at", LocalDateTime.class), ZoneOffset.UTC));
			return link;
		};
	}
}
