package ru.tinkoff.edu.java.linkparser.impl;

import ru.tinkoff.edu.java.linkparser.LinkParserBase;
import ru.tinkoff.edu.java.linkparser.dto.ParserLinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

public class StackOverflowParser extends LinkParserBase {
	private static final String LINK = "stackoverflow.com";
	public static final String PATH = "questions";

	@Override
	protected ParserResponse<ParserLinkDTO> parseInternal(URL url) {
		if (LINK.equals(url.getHost())) {
			int id = getQuestionId(getArrayFromURL(url));
			return new ParserResponse<>(new StackOverflowDTO(id));
		} else {
			return null;
		}
	}

	private int getQuestionId(List<String> path) throws NoSuchElementException, NumberFormatException {
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).equals(PATH)) {
				return Integer.parseInt(path.get(i + 1));
			}
		}
		throw new NoSuchElementException("Url has no 'questions' in path");
	}
}
