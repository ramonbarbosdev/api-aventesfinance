package com.api_aventesfinance.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PluggyPaginacaoResponse {
    private Integer total;
    private Integer totalPages;
    private Integer pages;
    private List<PluggyTransacaoResponse> results;

    public PluggyPaginacaoResponse() {
    }

    public List<PluggyTransacaoResponse> getResults() {
        return results;
    }
    public void setResults(List<PluggyTransacaoResponse> results) {
        this.results = results;
    }


    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
