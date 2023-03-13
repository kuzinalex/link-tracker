package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.dto.ParserLinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public abstract non-sealed class LinkParserBase implements LinkParser {

	public static LinkParser of(LinkParserBase... parsers) {

		for (int i = 0; i < parsers.length - 1; i++) {
			parsers[i].setNext(parsers[i + 1]);
		}
		return parsers[0];
	}

	protected LinkParserBase next;

	public void setNext(LinkParserBase next) {

		this.next = next;
	}

	@Override
	public final ParserResponse<ParserLinkDTO> parse(URL url) {

		ParserResponse<ParserLinkDTO> response = parseInternal(url);
		if (response != null) {
			return response;
		}else if (next==null){
			return null;
		}
		return next.parse(url);
	}

	protected abstract ParserResponse<ParserLinkDTO> parseInternal(URL url);

	protected List<String> getArrayFromURL(URL url) {
		return Arrays.asList(url.getPath().substring(1).split("/"));
	}
}

