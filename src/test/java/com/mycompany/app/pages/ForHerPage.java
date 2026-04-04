package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class ForHerPage extends BasePage {

    private final boolean isMobile;

    public ForHerPage(Page page, boolean isMobile) {
        super(page);
        this.isMobile = isMobile;
    }

    // DESKTOP APP LOCATORS

    private final String topPicksLink = ".collection-link a:has-text('Top Picks')";
    private final String productLinks = "#photoBookProductList .prod_url";

    // MOBILE APP LOCATORS

    private final String mobileTopPicksLink = "a[title='Top picks department']";

    public void clickTopPicks() {
        String locator = isMobile ? mobileTopPicksLink : topPicksLink;
        page.locator(locator).click();
    }

    public void clickFirstProduct() {
        page.locator(productLinks).first().click();
    }
}