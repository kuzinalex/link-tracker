package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.dto.ParserLinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;

import java.net.URL;

public sealed interface LinkParser permits LinkParserBase{
	ParserResponse<ParserLinkDTO> parse(URL url);
}
