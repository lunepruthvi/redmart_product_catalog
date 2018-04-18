package com.redmart.catalog.model;

import com.google.gson.annotations.Expose;

public class Product {

    @Expose
    String id;

    @Expose
    String title;

    @Expose
    String desc;

    public Product() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
