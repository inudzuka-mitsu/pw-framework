package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddressModal extends BasePage {

    public AddressModal(Page page) {
        super(page);
    }

    private final String shipToAddressBtn = "#divAddressList input[value='Ship To This Address']";
    private final String saveAndContinueBtn = "input#ctl00_belowHeader_saveContinueBtn";

    public void selectFirstAddressAndShip() {
        page.locator(shipToAddressBtn).first().click(new Locator.ClickOptions().setForce(true));
    }

    public void clickSaveAndContinue() {
        page.locator(saveAndContinueBtn).click();
    }
}
