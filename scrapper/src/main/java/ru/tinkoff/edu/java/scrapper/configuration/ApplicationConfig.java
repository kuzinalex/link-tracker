package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.linkparser.AbstractLinkParser;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.impl.GitHubLinkParser;
import ru.tinkoff.edu.java.linkparser.impl.StackOverflowLinkParser;

@Configuration
public class ApplicationConfig {

	@Bean
	public LinkParser linkParser( ){

		return AbstractLinkParser.of(new GitHubLinkParser(), new StackOverflowLinkParser());
	}
}
