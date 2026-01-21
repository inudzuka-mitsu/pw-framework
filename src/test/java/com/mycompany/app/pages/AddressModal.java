package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddressModal extends BasePage {

    public AddressModal(Page page) {
        super(page);
    }

    private final String shipToAddressBtn = "#divAddressList input[value='Ship To This Address']";
    private final String saveAndContinueBtn = "input#ctl00_belowHeader_saveContinueBtn";
    private final String addressTextLocator = ".cstAddress";
    private final String addressCardContainer = ".itembox";
    private final String addNewAddressBtn = "#ctl00_belowHeader_viewAddressBookControl_btn_addnewaddress";

    public void selectFirstAddressAndShip() {
        page.locator(shipToAddressBtn).first().click(new Locator.ClickOptions().setForce(true));
    }

    public void selectSecondAddressAndShip() {
        page.locator(shipToAddressBtn).nth(1).click(new Locator.ClickOptions().setForce(true));
    }

    public String selectFirstAddressAndReturnText() {
        Locator firstCard = page.locator(addressCardContainer).first();
        Locator addressBlock = firstCard.locator(addressTextLocator);

        String fullText = addressBlock.innerText();
        Locator nicknameLocator = addressBlock.locator(".addrName");

        String cleanAddress = fullText;
        
        if (nicknameLocator.count() > 0) {
            String nickname = nicknameLocator.innerText();
            cleanAddress = cleanAddress.replace(nickname, "");
        }
        cleanAddress = cleanAddress.replace("Phone:", "").trim();
        firstCard.locator("input[value='Ship To This Address']").click(new Locator.ClickOptions().setForce(true));
        return cleanAddress;
    }

    public String selectSecondAddressAndReturnText() {
        Locator firstCard = page.locator(addressCardContainer).nth(1);
        Locator addressBlock = firstCard.locator(addressTextLocator);
        String fullText = addressBlock.innerText();
        Locator nicknameLocator = addressBlock.locator(".addrName");

        String cleanAddress = fullText;

        if (nicknameLocator.count() > 0) {
            String nickname = nicknameLocator.innerText();
            cleanAddress = cleanAddress.replace(nickname, "");
        }

        cleanAddress = cleanAddress.replace("Phone:", "").trim();
        firstCard.locator("input[value='Ship To This Address']").click(new Locator.ClickOptions().setForce(true));
        return cleanAddress;
    }

    public void editAddress(int index) {
        Locator firstCard = page.locator(addressCardContainer).nth(index);
        firstCard.locator("input[value='Edit']").click(new Locator.ClickOptions().setForce(true));
    }

    public void clickSaveAndContinue() {
        page.locator(saveAndContinueBtn).click();
    }

    public void clickAddNewAddress() {
        page.locator(addNewAddressBtn).click();
    }

    public void validateLastAddress(String nickname, String firstName, String lastName, 
                                    String address, String city, String state, String zip, String phone) {
        Locator lastCard = page.locator(addressCardContainer).last();
        Locator addressBlock = lastCard.locator(addressTextLocator);
        
        assertThat(addressBlock).containsText(nickname);
        assertThat(addressBlock).containsText(firstName + " " + lastName);
        assertThat(addressBlock).containsText(address);
        assertThat(addressBlock).containsText(city);
        assertThat(addressBlock).containsText(state);
        assertThat(addressBlock).containsText(zip);
        assertThat(addressBlock).containsText(phone);
    }

    public void validateAddress(int index, String nickname, String firstName, String lastName, 
                                    String address, String city, String state, String zip, String phone) {
        Locator lastCard = page.locator(addressCardContainer).nth(index);
        Locator addressBlock = lastCard.locator(addressTextLocator);
        
        assertThat(addressBlock).containsText(nickname);
        assertThat(addressBlock).containsText(firstName + " " + lastName);
        assertThat(addressBlock).containsText(address);
        assertThat(addressBlock).containsText(city);
        assertThat(addressBlock).containsText(state);
        assertThat(addressBlock).containsText(zip);
        assertThat(addressBlock).containsText(phone);
    }
}
