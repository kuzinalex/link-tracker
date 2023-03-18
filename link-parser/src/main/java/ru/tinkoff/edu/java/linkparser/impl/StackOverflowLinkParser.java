package ru.tinkoff.edu.java.linkparser.impl;

import ru.tinkoff.edu.java.linkparser.AbstractLinkParser;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowLinkParserDTO;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

public class StackOverflowLinkParser extends AbstractLinkParser {

	private static final String HOST = "stackoverflow.com";
	public static final String PATH = "questions";

	@Override
	protected ParserResponse<LinkParserDTO> parseLink(URL url) {

		if (url.getPath().isEmpty()) {
			return null;
		}
		if (HOST.equals(url.getHost())) {
			String id = getQuestionId(getArrayFromURL(url));
			if (id == null) {
				return null;
			} else {
				return new ParserResponse<>(new StackOverflowLinkParserDTO(id));
			}
		} else {
			return null;
		}
	}

	private String getQuestionId(List<String> path) throws NoSuchElementException, NumberFormatException {

		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).equals(PATH)) {
				return path.get(i + 1);
			}
		}
		return null;
	}
}
