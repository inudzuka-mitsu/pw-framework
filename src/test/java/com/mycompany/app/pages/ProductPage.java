package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductPage extends BasePage {

    public ProductPage(Page page) {
        super(page); 
    }

    private final String personalizeBtn = "button#personalizeBtn";
    private final String handleColorDropdown = "#option-select-container select";

    public void clickPersonalizeBtn() {
        page.locator(personalizeBtn).click();
    }

    public void clickStartDesigning() {
        page.locator(personalizeBtn).click();
    }

    public void validateDefaultHandleColor(String expectedText) {
        Locator selectedOption = page.locator(handleColorDropdown).locator("option:checked");
        assertThat(selectedOption).containsText(expectedText);
    }
}
