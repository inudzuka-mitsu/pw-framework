package com.mycompany.app.pages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OrderConfirmationPage extends BasePage {

    public OrderConfirmationPage(Page page) {
        super(page); 
    }

    private final String successMessage = "text=Your order has been successfully entered into our system";

    public void verifyOrderSuccessMessage() {
        assertThat(page.locator(successMessage)).isVisible();
        assertThat(page.locator(successMessage)).containsText(
            "Your order has been successfully entered into our system. A complete order confirmation will be emailed to you."
        );
    }
}
