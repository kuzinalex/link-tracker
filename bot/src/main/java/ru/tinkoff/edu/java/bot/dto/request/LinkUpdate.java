package ru.tinkoff.edu.java.bot.dto.request;

public record LinkUpdate(Long id, String url, String description, Long[] tgChatIds) {

}
