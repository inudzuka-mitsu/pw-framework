package com.mycompany.app.pages.modals_popups;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mycompany.app.pages.BasePage;

public class Header extends BasePage {

    public Header(Page page) {
        super(page);
    }

    private final String hamburgerMenuIcon = "#menuToggle";
    private final String signInLink = "a.link__signin-my-account";
    private final String accountMenuTrigger = "a.link__signin-my-account";

    public void clickHamburgerMenu() {
        page.locator(hamburgerMenuIcon).click(new Locator.ClickOptions().setForce(true));
    }

    public void clickSignIn() {
        if(page.locator(accountMenuTrigger).isVisible()) {
             page.locator(accountMenuTrigger).hover();
        }
        page.locator(signInLink).click();
    }
}