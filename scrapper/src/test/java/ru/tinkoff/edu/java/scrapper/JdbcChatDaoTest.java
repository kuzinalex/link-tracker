package ru.tinkoff.edu.java.scrapper;

import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcChatDaoTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatDao dao;



    @Test
    @Transactional
    public void addTest() {
        assertThat(this.dao.add(anyLong())).isEqualTo(1);
    }
}
