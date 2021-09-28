package br.com.dio.stockquotesapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Table("quote")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Quote {

    @Id
    private Long id;
    private String symbol;
    private Double openValue;
    private Double closeValue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
