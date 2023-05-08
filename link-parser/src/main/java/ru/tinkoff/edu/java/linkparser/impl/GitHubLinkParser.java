package ru.tinkoff.edu.java.linkparser.impl;

import java.net.URL;
import java.util.List;
import ru.tinkoff.edu.java.linkparser.AbstractLinkParser;
import ru.tinkoff.edu.java.linkparser.dto.GitHubLinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;

public class GitHubLinkParser extends AbstractLinkParser {
    private static final String HOST = "github.com";

    @Override
    protected ParserResponse<LinkParserDTO> parseLink(URL url) {
        if (url.getPath().isEmpty()) {
            return null;
        }
        if (HOST.equals(url.getHost())) {
            List<String> values = getArrayFromURL(url);
            if (values.size() < 2) {
                return null;
            }
            return new ParserResponse<>(new GitHubLinkParserDTO(values.get(0), values.get(1)));
        } else {
            return null;
        }
    }
}
