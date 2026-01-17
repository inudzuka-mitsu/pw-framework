package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class ProductPage extends BasePage {

    public ProductPage(Page page) {
        super(page); 
    }

    private final String personalizeBtn = "button#personalizeBtn";

    public void clickPersonalizeBtn() {
        page.locator(personalizeBtn).click();
    }
}
