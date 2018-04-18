package com.redmart.catalog.views;


import com.redmart.catalog.model.productList.Image;
import com.redmart.catalog.model.productList.Primary;

import java.util.List;

public interface ProductDetailsView {


    void updateTitleAndSizeDetails(String productName, String productDetails);

    void updateAmountDetails(String currentAmount, String originalAmount, String savingMsg, int savingBgColor);

    void updateImageGallery(List<Image> images);

    void updateProductMetaDescriptions(List<Primary> metaDetailsList);

    void showThisErrorMsg(int stringResourceId);

}
