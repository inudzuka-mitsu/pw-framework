package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Header extends BasePage {

    public Header(Page page) {
        super(page);
    }

    private final String hamburgerMenuIcon = "#menuToggle";

    public void clickHamburgerMenu() {
        page.locator(hamburgerMenuIcon).click(new Locator.ClickOptions().setForce(true));
    }
}