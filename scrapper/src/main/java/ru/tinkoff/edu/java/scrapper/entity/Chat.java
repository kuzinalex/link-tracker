package ru.tinkoff.edu.java.scrapper.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@NoArgsConstructor
@Data
public class Chat {
    @Id
    private Long id;
}
