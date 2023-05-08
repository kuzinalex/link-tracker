package ru.tinkoff.edu.java.linkparser;

import java.net.URL;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;

public sealed interface LinkParser permits AbstractLinkParser {
    ParserResponse<LinkParserDTO> parse(URL url);
}
