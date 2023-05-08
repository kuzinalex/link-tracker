package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubEvent(Long id, String type, Payload payload, @JsonProperty(value = "created_at") OffsetDateTime createdAt) {

    public record Payload(Commit[] commits, @JsonProperty(value = "pull_request") PullRequest pullRequest) {

        public record Commit(String sha, String message) {

        }

        public record PullRequest(String url, String title) {

        }
    }
}
