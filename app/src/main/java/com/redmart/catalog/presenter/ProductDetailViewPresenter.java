package com.redmart.catalog.presenter;

import android.content.Context;

import com.redmart.catalog.R;
import com.redmart.catalog.model.productList.Primary;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.services.DetailViewService;
import com.redmart.catalog.utils.Utils;
import com.redmart.catalog.views.ProductDetailsView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailViewPresenter {

    private Context context;
    private DetailViewService detailViewService;
    private ProductDetailsView productDetailsView;


    public ProductDetailViewPresenter(Context context, ProductDetailsView productDetailsView, DetailViewService detailViewService) {
        this.context = context;
        this.detailViewService = detailViewService;
        this.productDetailsView = productDetailsView;
    }

    public void displayAllDetails() {
        Product selectedProduct = detailViewService.getSelectedProduct();
        if (selectedProduct == null) {
            productDetailsView.showThisErrorMsg(R.string.err_productDetailsNotFound);
            return;
        }


        productDetailsView.updateTitleAndSizeDetails(selectedProduct.getTitle(), selectedProduct.getMeasure() != null ? selectedProduct.getMeasure().getWtOrVol() : null);

        productDetailsView.updateImageGallery(selectedProduct.getImages());

        Double displayAmount = null;
        Double originalAmount = null;
        int colorResId = R.color.promoType_others;

        if (selectedProduct.getPricing() != null) {
            if (selectedProduct.getPricing().getPromoPrice() != null && selectedProduct.getPricing().getPromoPrice() > 0) {
                displayAmount = selectedProduct.getPricing().getPromoPrice();
                originalAmount = selectedProduct.getPricing().getPrice();
            } else {
                displayAmount = selectedProduct.getPricing().getPrice();
            }

            if (selectedProduct.getPricing().getSavingsType() != null) {
                if (selectedProduct.getPricing().getSavingsType() == 1) {
                    colorResId = R.color.promoType_1;
                } else if (selectedProduct.getPricing().getSavingsType() == 2) {
                    colorResId = R.color.promoType_2;
                } else {
                    colorResId = R.color.promoType_others;
                }
            }

            productDetailsView.updateAmountDetails(Utils.formatAmountToString(displayAmount), Utils.formatAmountToString(originalAmount), selectedProduct.getPricing().getSavingsText(), colorResId);
        }

        if (selectedProduct.getDescriptionFields() != null) {
            List<Primary> descList = new ArrayList<>();

            if (selectedProduct.getDescriptionFields().getPrimary() != null) {
                for (Primary primary : selectedProduct.getDescriptionFields().getPrimary()) {
                    if (!"".equals(primary.getName()) && !"".equals(primary.getContent())) {
                        descList.add(primary);
                    }
                }
            }

            if (selectedProduct.getDescriptionFields().getSecondary() != null) {
                for (Primary primary : selectedProduct.getDescriptionFields().getSecondary()) {
                    if (!"".equals(primary.getName()) && !"".equals(primary.getContent())) {
                        descList.add(primary);
                    }
                }

            }

            if (descList.size() > 0) {
                productDetailsView.updateProductMetaDescriptions(descList);
            }
        }
    }
}
