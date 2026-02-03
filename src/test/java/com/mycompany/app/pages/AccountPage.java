package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class AccountPage extends BasePage {

    public AccountPage(Page page) {
        super(page);
    }

    private final String manageShippingAddressLink = "a:has-text('Manage my shipping address')";
    private final String orderHistoryLink = "a:has-text('Order History')";

    public void clickManageShippingAddress() {
        page.locator(manageShippingAddressLink).first().click();
    }

    public void clickOrderHistory() {
        page.locator(orderHistoryLink).first().click();
    }
}