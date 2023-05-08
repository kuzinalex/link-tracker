package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.common.exception.DuplicateChatException;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.entity.Chat;

public class JdbcChatDao implements ChatDao {

    private static final String SQL_INSERT_CHAT = "INSERT INTO chat VALUES (?)";
    private static final String SQL_REMOVE_CHAT = "DELETE FROM chat WHERE id=(?)";
    private static final String SQL_FIND_CHATS_BY_LINK = "SELECT chat_id FROM subscription WHERE link_id=(?)";
    private static final String SQL_FIND_ALL_CHATS = "SELECT * FROM chat";
    private JdbcTemplate jdbcTemplate;

    public JdbcChatDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Long tgChatId) throws DuplicateChatException {

        try {
            return jdbcTemplate.update(SQL_INSERT_CHAT, ps -> {
                ps.setLong(1, tgChatId);
            });
        } catch (DuplicateKeyException e) {
            throw new DuplicateChatException(e.getMessage());
        }

    }

    @Override
    public int remove(Long tgChatId) {

        return jdbcTemplate.update(SQL_REMOVE_CHAT, ps -> {
            ps.setLong(1, tgChatId);
        });
    }

    @Override
    public List<Long> findLinkSubscribers(Long linkId) {

        return jdbcTemplate.query(SQL_FIND_CHATS_BY_LINK, ps -> {
            ps.setLong(1, linkId);
        }, (rs, rowNum) -> rs.getLong("chat_id"));
    }

    @Override
    public List<Chat> findAll() {

        return jdbcTemplate.query(SQL_FIND_ALL_CHATS, chatRowMapper());
    }

    private RowMapper<Chat> chatRowMapper() {

        return (rs, rowNum) -> {
            Chat chat = new Chat();
            chat.setId(rs.getLong("id"));
            return chat;
        };
    }
}
