package ru.tinkoff.edu.java.linkparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import ru.tinkoff.edu.java.linkparser.dto.GitHubLinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowLinkParserDTO;
import ru.tinkoff.edu.java.linkparser.impl.GitHubLinkParser;
import ru.tinkoff.edu.java.linkparser.impl.StackOverflowLinkParser;

import java.net.MalformedURLException;
import java.net.URL;


public class LinkParserTest {

	public static final LinkParser PARSER = AbstractLinkParser.of(new GitHubLinkParser(), new StackOverflowLinkParser());

	@Test
	public void testGitHubSuccess() throws MalformedURLException {

		URL url = new URL("https://github.com/user/repository");
		ParserResponse<LinkParserDTO> response = PARSER.parse(url);
		assertEquals(new GitHubLinkParserDTO("user", "repository"), response.response());
	}

	@Test
	public void testGitHubNull() throws MalformedURLException {

		URL url = new URL("https://github.com");
		ParserResponse<LinkParserDTO> response = PARSER.parse(url);
		assertNull(response);

		url = new URL("https://github.com/rghrthr");
		response = PARSER.parse(url);
		assertNull(response);
	}


	@Test
	public void testStackOverflowSuccess() throws MalformedURLException {

		URL url = new URL("https://stackoverflow.com/questions/111/some-question-name");
		ParserResponse<LinkParserDTO> response = PARSER.parse(url);
		assertEquals(new StackOverflowLinkParserDTO("111"), response.response());
	}

	@Test
	public void testStackOverflowNull() throws MalformedURLException {

		URL url = new URL("https://stackoverflow.com");
		ParserResponse<LinkParserDTO> response = PARSER.parse(url);
		assertNull(response);

		url = new URL("https://stackoverflow.com/search?q=unsupported%20link");
		response = PARSER.parse(url);
		assertNull(response);
	}
	@Test
	public void testUnknownHost() throws MalformedURLException {

		URL url = new URL("https://unknown.com/questions/111/some-question-name");
		ParserResponse<LinkParserDTO> response = PARSER.parse(url);
		assertNull(response);
	}
}
