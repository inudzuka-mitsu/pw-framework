package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class ForHerPage extends BasePage {

    public ForHerPage(Page page) {
        super(page);
    }

    private final String topPicksLink = ".collection-link a:has-text('Top Picks')";
    private final String productLinks = "#photoBookProductList .prod_url";

    public void clickTopPicks() {
        page.locator(topPicksLink).click();
    }

    public void clickFirstProduct() {
        page.locator(productLinks).first().click();
    }
}