package ru.tinkoff.edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcSubscriptionDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqChatDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqLinkDao;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqSubscriptionDao;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class IntegrationEnvironment {

	protected static final PostgreSQLContainer<?> SQL_CONTAINER;
	private static final String MASTER_PATH = "migrations/master.xml";

	@Configuration
	static class IntegrationEnvironmentConfiguration {

		@Bean
		public DataSource dataSource() {

			return DataSourceBuilder.create()
					.url(SQL_CONTAINER.getJdbcUrl())
					.username(SQL_CONTAINER.getUsername())
					.password(SQL_CONTAINER.getPassword())
					.build();
		}

		@Bean
		JdbcTemplate jdbcTemplate() {

			return new JdbcTemplate(dataSource());
		}

		@Bean
		JdbcChatDao jdbcChatDao() {

			return new JdbcChatDao(jdbcTemplate());
		}

		@Bean
		JdbcLinkDao jdbcLinkDao() {

			return new JdbcLinkDao(jdbcTemplate());
		}

		@Bean
		JdbcSubscriptionDao jdbcSubscriptionDao() {

			return new JdbcSubscriptionDao(jdbcTemplate());
		}

		@Bean
		public DSLContext dslContext() {
			return DSL.using(dataSource(), SQLDialect.POSTGRES);
		}

		@Bean
		public JooqChatDao jooqChatDao() {
			return new JooqChatDao(dslContext());
		}

		@Bean
		public JooqLinkDao jooqLinkDAO() {
			return new JooqLinkDao(dslContext());
		}

		@Bean
		public JooqSubscriptionDao jooqSubscriptionDAO() {
			return new JooqSubscriptionDao(dslContext());
		}

		@Bean
		PlatformTransactionManager platformTransactionManager() {

			JdbcTransactionManager transactionManager = new JdbcTransactionManager();
			transactionManager.setDataSource(dataSource());
			return transactionManager;
		}
	}

	static {
		SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");
		SQL_CONTAINER.start();

		try {
			Connection connection = getConnection();
			Path path = new File("../").toPath().toAbsolutePath().normalize();
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
			Liquibase liquibase = new liquibase.Liquibase(MASTER_PATH,
					new DirectoryResourceAccessor(path), database);
			liquibase.update(new Contexts(), new LabelExpression());
		} catch (SQLException | LiquibaseException | FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(
				SQL_CONTAINER.getJdbcUrl(),
				SQL_CONTAINER.getUsername(),
				SQL_CONTAINER.getPassword()
		);
	}
}