package ru.tinkoff.edu.java.scrapper.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Data
public class Chat {
    @Id
    private Long id;
}
