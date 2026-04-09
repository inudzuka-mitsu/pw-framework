package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductPage extends BasePage {

    private final boolean isMobile;

    public ProductPage(Page page, boolean isMobile) {
        super(page); 
        this.isMobile = isMobile;
    }

    // DESKTOP LOCATORS

    private final String personalizeBtn = "button#personalizeBtn";
    private final String handleColorDropdown = "#option-select-container select";

    // MOBILE APP LOCATORS

    private final String mobilePersonalizeBtn = "div_add_to_cart a";

    public void clickPersonalizeBtn() {
        String locator = isMobile ? mobilePersonalizeBtn: personalizeBtn;
        page.locator(locator).click();
    }

    public void clickStartDesigning() {
        page.locator(personalizeBtn).click();
    }

    public void validateDefaultHandleColor(String expectedText) {
        Locator selectedOption = page.locator(handleColorDropdown).locator("option:checked");
        assertThat(selectedOption).containsText(expectedText);
    }
}
