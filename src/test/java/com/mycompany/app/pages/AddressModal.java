package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddressModal extends BasePage {

    public AddressModal(Page page) {
        super(page);
    }

    private final String shipToAddressBtn = "#divAddressList input[value='Ship To This Address']";

    public void selectFirstAddressAndShip() {
        page.locator(shipToAddressBtn).first().click(new Locator.ClickOptions().setForce(true));
    }
}
