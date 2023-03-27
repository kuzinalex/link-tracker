package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record StackOverflowResponse(Item[] items) {

	private record Item(String question_id, OffsetDateTime last_activity_date, OffsetDateTime creation_date,
						OffsetDateTime last_edit_date) {

	}
}
