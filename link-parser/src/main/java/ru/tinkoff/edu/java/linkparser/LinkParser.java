package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;

import java.net.URL;

public sealed interface LinkParser permits AbstractLinkParser {
	ParserResponse<LinkParserDTO> parse(URL url);
}
