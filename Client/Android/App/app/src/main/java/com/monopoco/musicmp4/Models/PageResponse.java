package com.monopoco.musicmp4.Models;

public class PageResponse<T> {

    private Integer pageSize;
    private Integer pageNumber;
    private Long dataCount;


    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageResponse data(T data) {
        this.data = data;
        return this;
    }

    public PageResponse dataCount(Long dataCount) {
        this.dataCount = dataCount;
        return this;
    }

    public PageResponse dataCount(int dataCount) {
        this.dataCount = Long.valueOf(dataCount);
        return this;
    }

    public PageResponse pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageResponse pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }
}
