package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GitHubEventType {

    PUSH_EVENT("PushEvent"),
    PULL_REQUEST_EVENT("PullRequestEvent");

    private final String value;
}
