package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CelebrationsPassportPage extends BasePage {

    public CelebrationsPassportPage(Page page) {
        super(page);
    }

    private final String signUpButton = "#btn_add-free-ship";
    private final String heroHeader = "h2:has-text('Unlimited free shipping')";
    private final String benefitsSection = "ul.list__four-blocks";

    public void clickSignUp() {
        page.locator(signUpButton).click(new Locator.ClickOptions().setForce(true));
    }

    public void verifyPassportPageLoaded() {
        assertThat(page.locator(heroHeader)).isVisible();
        assertThat(page.locator(signUpButton)).isVisible();
        assertThat(page.locator(benefitsSection)).isVisible();
        System.out.println("Passport Landing Page loaded successfully.");
    }

    public void validatePriceOnButton(String expectedPrice) {
        assertThat(page.locator(signUpButton)).containsText(expectedPrice);
    }
}
