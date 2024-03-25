package com.example.crockpot.models;

import java.util.List;

public class RecipesResponse {
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<RecipeDto> getResults() {
        return results;
    }

    public void setResults(List<RecipeDto> results) {
        this.results = results;
    }

    public RecipesResponse(int page, int totalPages, List<RecipeDto> results) {
        this.page = page;
        this.totalPages = totalPages;
        this.results = results;
    }

    private int page;
    private int totalPages;
    private List<RecipeDto> results;
}
