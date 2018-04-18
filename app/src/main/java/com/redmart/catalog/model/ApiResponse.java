package com.redmart.catalog.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class ApiResponse {

    @Expose
    ArrayList<Product> products;

    @Expose
    int page;

    @Expose
    int page_size;

    public ApiResponse() {

    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
