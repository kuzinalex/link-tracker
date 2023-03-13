package ru.tinkoff.edu.java.linkparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserLinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;
import ru.tinkoff.edu.java.linkparser.impl.GitHubParser;
import ru.tinkoff.edu.java.linkparser.impl.StackOverflowParser;

import java.net.MalformedURLException;
import java.net.URL;


public class ParserTest {

	public static final LinkParser PARSER = LinkParserBase.of(new GitHubParser(), new StackOverflowParser());

	@Test
	public void testGitHubSuccess() throws MalformedURLException {

		URL url = new URL("https://github.com/user/repository");
		ParserResponse<ParserLinkDTO> response = PARSER.parse(url);
		assertEquals(new GitHubDTO("user", "repository"), response.response());
	}

	@Test
	public void testStackOverflowSuccess() throws MalformedURLException {

		URL url = new URL("https://stackoverflow.com/questions/111/some-question-name");
		ParserResponse<ParserLinkDTO> response = PARSER.parse(url);
		assertEquals(new StackOverflowDTO(111), response.response());
	}

	@Test(expected = NumberFormatException.class)
	public void testStackOverflowNumberFormatException() throws MalformedURLException {

		URL url = new URL("https://stackoverflow.com/questions/11ddsd1/some-question-name");
		PARSER.parse(url);
	}

	@Test
	public void testUnknownHost() throws MalformedURLException {

		URL url = new URL("https://some.com/questions/111/some-question-name");
		ParserResponse<ParserLinkDTO> response = PARSER.parse(url);
		assertNull(response);
	}
}
