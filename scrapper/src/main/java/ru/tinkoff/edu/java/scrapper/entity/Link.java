package ru.tinkoff.edu.java.scrapper.entity;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Link {
    @Id
    private Long id;
    private String url;
    private OffsetDateTime checkTime;
    private OffsetDateTime updatedAt;
}
