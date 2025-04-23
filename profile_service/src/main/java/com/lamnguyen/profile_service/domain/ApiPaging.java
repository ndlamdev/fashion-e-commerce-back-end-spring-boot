package com.lamnguyen.profile_service.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiPaging<T> {
    List<T> content;
    int current;
    int size;
    int totalPage;
}
