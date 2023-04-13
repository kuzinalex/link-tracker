package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDao;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcSubscriptionDao implements SubscriptionDao {

	private final String SQL_INSERT_SUBSCRIPTION = "INSERT INTO subscription  VALUES (?,?)";
	private final String SQL_REMOVE_SUBSCRIPTION = "DELETE FROM subscription WHERE chat_id=(?) AND link_id=(?)";
	private final String SQL_FIND_ALL_SUBSCRIPTIONS = "SELECT * FROM subscription WHERE chat_id=(?)";
	private final String SQL_FIND_SUBSCRIPTION = "SELECT * FROM subscription WHERE chat_id=(?) AND link_id=(?)";

	private final JdbcTemplate jdbcTemplate;

	public JdbcSubscriptionDao(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}

	public int add(Long tgChatId, Long linkId) {

		String insertQuery = SQL_INSERT_SUBSCRIPTION;
		return jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, tgChatId);
			ps.setLong(2, linkId);
			return ps;
		});
	}

	public List<Subscription> findAll(Long tgChatId) {

		return jdbcTemplate.query(SQL_FIND_ALL_SUBSCRIPTIONS, ps -> {
			ps.setLong(1, tgChatId);
		}, subscriptionRowMapper());
	}

	@Override
	public Subscription find(Long tgChatId, Long linkId) {

		return jdbcTemplate.query(SQL_FIND_SUBSCRIPTION, ps -> {
			ps.setLong(1, tgChatId);
			ps.setLong(2, linkId);
		}, subscriptionRowMapper()).stream().findFirst().orElse(null);
	}

	public int remove(Long tgChatId, Long linkId) {

		return jdbcTemplate.update(SQL_REMOVE_SUBSCRIPTION, ps -> {
			ps.setLong(1, tgChatId);
			ps.setLong(2, linkId);
		});
	}

	private RowMapper<Subscription> subscriptionRowMapper() {

		return (rs, rowNum) -> {
			Subscription subscription = new Subscription();
			subscription.setLinkId(rs.getLong("link_id"));
			subscription.setChatId(rs.getLong("chat_id"));
			return subscription;
		};
	}
}
