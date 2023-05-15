package ru.tinkoff.edu.java.linkparser;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import ru.tinkoff.edu.java.linkparser.dto.LinkParserDTO;
import ru.tinkoff.edu.java.linkparser.dto.ParserResponse;

public abstract non-sealed class AbstractLinkParser implements LinkParser {

    public static LinkParser of(AbstractLinkParser... parsers) {

        for (int i = 0; i < parsers.length - 1; i++) {
            parsers[i].setNext(parsers[i + 1]);
        }
        return parsers[0];
    }

    protected AbstractLinkParser next;

    public void setNext(AbstractLinkParser next) {

        this.next = next;
    }

    @Override
    public final ParserResponse<LinkParserDTO> parse(URL url) {

        ParserResponse<LinkParserDTO> response = parseLink(url);
        if (response != null) {
            return response;
        } else if (next == null) {
            return new ParserResponse<>(null);
        }
        return next.parse(url);
    }

    protected abstract ParserResponse<LinkParserDTO> parseLink(URL url);

    protected List<String> getArrayFromURL(URL url) {
        return Arrays.asList(url.getPath().substring(1).split("/"));
    }
}

