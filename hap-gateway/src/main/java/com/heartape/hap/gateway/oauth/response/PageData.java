package com.heartape.hap.gateway.oauth.response;

import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<T> records;

    public PageData(int current, int size, int total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
        this.pages = total % size == 0 ? total / size : total / size + 1;
    }
}
