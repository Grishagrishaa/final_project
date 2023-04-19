package com.example.userservice.controllers.pagination;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
@Builder(setterPrefix = "set")
public class MyPage<T> {
    private final Integer number;
    private final Integer size;
    private final Integer totalPages;
    private final Long totalElements;
    private final Boolean first;
    private final Integer numberOfElements;
    private final Boolean last;
    private final List<T> content;
}
