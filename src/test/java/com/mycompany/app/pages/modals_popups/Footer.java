package com.mycompany.app.pages.modals_popups;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mycompany.app.pages.BasePage;

public class Footer extends BasePage {

    public Footer(Page page) {
        super(page);
    }

    private final String celebrationsPassportLink = "a:has-text('Celebrations Passport')";

    public void clickCelebrationsPassport() {
        Locator link = page.locator(celebrationsPassportLink).first();
        link.scrollIntoViewIfNeeded();
        link.click();
    }
}