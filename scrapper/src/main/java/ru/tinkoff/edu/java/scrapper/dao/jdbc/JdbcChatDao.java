package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.entity.Chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcChatDao implements ChatDao {

	public JdbcChatDao(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;
	}

	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int add(Long tgChatId) throws DuplicateChatException {

		String query = "INSERT INTO chat VALUES (?)";
		try {
			return jdbcTemplate.update(query, ps -> {
				ps.setLong(1, tgChatId);
			});
		}catch (DuplicateKeyException e){
			throw new DuplicateChatException(e.getMessage());
		}

	}

	@Transactional
	@Override
	public int remove(Long tgChatId) {

		String query = "DELETE FROM chat WHERE id=(?)";
		return jdbcTemplate.update(query, ps -> {
			ps.setLong(1, tgChatId);
		});
	}

	@Transactional
	@Override
	public List<Long> findByLink(Long linkId) {

		String query = "SELECT chat_id FROM subscription WHERE link_id=(?)";
		return jdbcTemplate.query(query, ps -> {
			ps.setLong(1, linkId);
		}, (rs, rowNum) -> rs.getLong("chat_id"));
	}

	@Transactional
	@Override
	public List<Chat> findAll() {

		String query = "SELECT * FROM chat";
		return jdbcTemplate.query(query, chatRowMapper());
	}

	private RowMapper<Chat> chatRowMapper() {

		return (rs, rowNum) -> {
			Chat chat = new Chat();
			chat.setId(rs.getLong("id"));
			return chat;
		};
	}
}
