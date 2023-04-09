package ru.tinkoff.edu.java.scrapper.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Data
public class Link {
    @Id
    private Long id;
    private String url;
    private OffsetDateTime checkTime;
    private OffsetDateTime updatedAt;
}
