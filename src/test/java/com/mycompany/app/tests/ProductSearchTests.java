package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.ProductCatalogPage;
import com.mycompany.app.pages.login.StagingLoginPage;
import com.mycompany.app.pages.modals_popups.ProductModal;

public class ProductSearchTests extends TestBase {

     @Test
     void searchForProduct() {

        StagingLoginPage lp = new StagingLoginPage(page);
        HomePage hp = new HomePage(page);
        ProductModal modal = new ProductModal(page);
        ProductCatalogPage pc = new ProductCatalogPage(page);

        String productName = "Socks";

        page.navigate(getProperty("stagingBaseUrl"));
        lp.closePopUp();
        hp.typeProduct(productName);

        modal.validateCategoryName(productName);
        modal.validateAllProductsContainProductName(productName);
        modal.validateAllProductSuggestionsContainProductName(productName);

        hp.searchProduct();
        pc.validateCurrentSelection(productName);
        pc.validateItemSearchResults(productName);
     }
 }