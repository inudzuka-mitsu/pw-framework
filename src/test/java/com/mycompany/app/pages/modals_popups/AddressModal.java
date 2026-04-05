package com.mycompany.app.pages.modals_popups;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.mycompany.app.pages.BasePage;

public class AddressModal extends BasePage {

    private final boolean isMobile;

    public AddressModal(Page page, boolean isMobile) {
        super(page);
        this.isMobile = isMobile;
    }

    // DESKTOP APP LOCATORS

    private final String shipToAddressBtn = "#divAddressList input[value='Ship To This Address']";
    private final String saveAndContinueBtn = "input#ctl00_belowHeader_saveContinueBtn";
    private final String addressTextLocator = ".cstAddress";
    private final String addressCardContainer = ".itembox";
    private final String addNewAddressBtn = "#ctl00_belowHeader_viewAddressBookControl_btn_addnewaddress";

    // MOBILE APP LOCATORS

    private final String mobileAddNewAddressBtn = "button#newAddressSubmit";
    private final String mobileAddressTextLocator = ".adressbook-address";
    private final String mobileAddressCardContainer = ".box__address-block";

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
        String activeCardContainer = isMobile ? mobileAddressCardContainer : addressCardContainer;
        Locator card = page.locator(activeCardContainer).nth(index);
        
        String editBtnLocator = isMobile ? "a:has-text('Edit')" : "input[value='Edit']";
        
        card.locator(editBtnLocator).click(new Locator.ClickOptions().setForce(true));
    }

    public void clickSaveAndContinue() {
        page.locator(saveAndContinueBtn).click();
    }

    public void clickAddNewAddress() {
        String locator = isMobile ? mobileAddNewAddressBtn : addNewAddressBtn;
        page.locator(locator).click();
    }

    public void validateLastAddress(String nickname, String firstName, String lastName, 
                                    String address, String city, String state, String zip, String phone) {
        String activeCardContainer = isMobile ? mobileAddressCardContainer : addressCardContainer;
        String activeTextLocator = isMobile ? mobileAddressTextLocator : addressTextLocator;

        Locator lastCard = page.locator(activeCardContainer).last();
        Locator addressBlock = lastCard.locator(activeTextLocator);
        
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
        String activeCardContainer = isMobile ? mobileAddressCardContainer : addressCardContainer;
        String activeTextLocator = isMobile ? mobileAddressTextLocator : addressTextLocator;

        Locator card = page.locator(activeCardContainer).nth(index);
        Locator addressBlock = card.locator(activeTextLocator);
        
        assertThat(addressBlock).containsText(nickname);
        assertThat(addressBlock).containsText(firstName + " " + lastName);
        assertThat(addressBlock).containsText(address);
        assertThat(addressBlock).containsText(city);
        assertThat(addressBlock).containsText(state);
        assertThat(addressBlock).containsText(zip);
        assertThat(addressBlock).containsText(phone);
    }
}
