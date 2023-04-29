package ru.tinkoff.edu.java.scrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
	private Long chatId;
	private Long linkId;
}
