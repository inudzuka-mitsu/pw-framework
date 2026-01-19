package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LeftNav extends BasePage {

    public LeftNav(Page page) {
        super(page);
    }

    private final String productsMenuLink = "#productsMenuLink";
    private final String giftSetsLink = "#productsSubMenu a[href*='Personalized-Gift-Sets-s75.store']";

    public void clickProductsMenu() {
        page.locator(productsMenuLink).click();
    }

    public void clickGiftSets() {
        page.locator(giftSetsLink).click(new Locator.ClickOptions().setForce(true));
    }
}