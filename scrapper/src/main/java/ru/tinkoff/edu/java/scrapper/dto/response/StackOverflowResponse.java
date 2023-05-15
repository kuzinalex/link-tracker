package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowResponse(Item[] items) {

    public record Item(@JsonProperty(value = "question_id") String questionId, @JsonProperty(value = "last_activity_date") OffsetDateTime lastActivityDate,
                       @JsonProperty(value = "creation_date") OffsetDateTime creationDate,
                       @JsonProperty(value = "last_edit_date") OffsetDateTime lastEditDate) {

    }
}
