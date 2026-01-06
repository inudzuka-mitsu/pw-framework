package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.ProductCatalogPage;
import com.mycompany.app.pages.ProductModal;
import com.mycompany.app.pages.StagingLoginPage;

public class ProductSearchTests extends TestBase {

    @Test
    void searchForProduct() {
        String username = getProperty("username");
        String password = getProperty("password");
        String loginUrl = getProperty("baseUrl");

        StagingLoginPage lp = new StagingLoginPage(page);
        HomePage hp = new HomePage(page);
        ProductModal modal = new ProductModal(page);
        ProductCatalogPage pc = new ProductCatalogPage(page);

        String productName = "Socks";

        page.navigate(loginUrl);
        lp.login(username, password);
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