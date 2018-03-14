package com.wongxinjie.hackernews.bean;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public class PageResultBean<T> implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<T> rows;
    private int page;
    private int pageSize;
    private long totalItems;
    private int totalPages;

    public PageResultBean(List<T> rows, int page, int pageSize, long totalItems, int totalPages) {
        this.rows = rows;
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public PageResultBean(Page<T> paginator) {
        this.rows =  paginator.getContent();
        this.page = paginator.getNumber() + 1;
        this.pageSize = paginator.getSize();
        this.totalItems = paginator.getTotalElements();
        this.totalPages = paginator.getTotalPages();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
