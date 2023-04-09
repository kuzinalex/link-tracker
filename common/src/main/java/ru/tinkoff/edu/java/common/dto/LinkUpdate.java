package ru.tinkoff.edu.java.common.dto;

public record LinkUpdate(Long id, String url, String description, Long[] tgChatIds) {

}
