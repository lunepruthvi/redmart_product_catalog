package com.redmart.catalog.services;


import com.redmart.catalog.model.productList.Product;

public class DetailViewService {

    private Product selectedProduct;

    public DetailViewService(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

}
