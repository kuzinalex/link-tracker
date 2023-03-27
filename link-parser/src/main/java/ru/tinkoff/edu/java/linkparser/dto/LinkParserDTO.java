package ru.tinkoff.edu.java.linkparser.dto;

public sealed interface LinkParserDTO permits GitHubLinkParserDTO, StackOverflowLinkParserDTO {

}
