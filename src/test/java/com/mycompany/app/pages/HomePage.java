package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class HomePage extends BasePage {
    
    public HomePage(Page page) {
        super(page); 
    }

    private final String searchBar = "#searchBox";

    public void typeProduct(String productName) {
       page.locator(searchBar).click();
       page.locator(searchBar).fill(productName);
    }

    public void searchProduct() {
        page.locator(searchBar).press("Enter");
    }
}