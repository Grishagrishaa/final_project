package com.example.classifier.service.dto;

import java.util.List;

public class MyPage<T> {
    private final Integer number;
    private final Integer size;
    private final Integer totalPages;
    private final Long totalElements;
    private final Boolean first;
    private final Integer numberOfElements;
    private final Boolean last;
    private final List<T> content;

    public MyPage(Builder<T> builder) {
        this.number = builder.number;
        this.size = builder.size;
        this.totalPages = builder.totalPages;
        this.totalElements = builder.totalElements;
        this.first = builder.first;
        this.numberOfElements = builder.numberOfElements;
        this.last = builder.last;
        this.content = builder.content;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public Boolean getLast() {
        return last;
    }

    public List<T> getContent() {
        return content;
    }

    public static class Builder <T>{
        private Integer number;
        private Integer size;
        private Integer totalPages;
        private Long totalElements;
        private Boolean first;
        private Integer numberOfElements;
        private Boolean last;
        private List<T> content;

        public Builder<T> setNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Builder<T> setSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder<T> setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> setTotalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> setFirst(Boolean first) {
            this.first = first;
            return this;
        }

        public Builder<T> setNumberOfElements(Integer numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public Builder<T> setLast(Boolean last) {
            this.last = last;
            return this;
        }

        public Builder<T> setContent(List<T> content) {
            this.content = content;
            return this;
        }

        public static <T> Builder<T> create(){
            return new Builder<T>();
        }

        public MyPage<T> build(){
            return new MyPage<T>(this);
        }
    }
}
