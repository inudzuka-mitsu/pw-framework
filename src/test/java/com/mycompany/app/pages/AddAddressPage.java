package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class AddAddressPage extends BasePage {

    public AddAddressPage(Page page) {
        super(page);
    }

    private final String nicknameInput = "#ctl00_belowHeader_viewAddressBookControl_txtName";
    private final String firstNameInput = "#ctl00_belowHeader_viewAddressBookControl_txtFirstName";
    private final String lastNameInput = "#ctl00_belowHeader_viewAddressBookControl_txtLastName";
    private final String phoneInput = "#ctl00_belowHeader_viewAddressBookControl_txtPhone";
    private final String addressInput = "#ctl00_belowHeader_viewAddressBookControl_txtAddress1";
    private final String cityInput = "#ctl00_belowHeader_viewAddressBookControl_txtCity";
    private final String stateDropdown = "#ctl00_belowHeader_viewAddressBookControl_txtState";
    private final String zipInput = "#ctl00_belowHeader_viewAddressBookControl_txtZip";
    private final String addAddressBtn = "#cmdAddAddress";
    private final String saveAddress = "#cmdSaveAddress";

    private final String confirmCheckbox = "input#checkConfirm";
    private final String useThisAddressBtn = "input#cmdUseThisAddress";

    public void clickAddAddress() {
        page.locator(addAddressBtn).click();
    }

    public void clickSaveAddress() {
        page.locator(saveAddress).click();
    }

    public void fillNewAddressFormAndSubmit(String nickname, String fName, String lName, String phone, String address, String city, String stateCode, String zip) {
        page.locator(nicknameInput).clear();
        page.locator(nicknameInput).fill(nickname);
        page.locator(firstNameInput).clear();
        page.locator(firstNameInput).fill(fName);
        page.locator(lastNameInput).clear();
        page.locator(lastNameInput).fill(lName);
        page.locator(phoneInput).clear();
        page.locator(phoneInput).fill(phone);
        page.locator(addressInput).clear();
        page.locator(addressInput).fill(address);
        page.locator(cityInput).clear();
        page.locator(cityInput).fill(city);

        page.locator(stateDropdown).selectOption(stateCode);
        
        page.locator(zipInput).clear();
        page.locator(zipInput).fill(zip);
    }

    public void confirmVerifiedAddress() {
        page.locator(confirmCheckbox).check(new com.microsoft.playwright.Locator.CheckOptions().setForce(true));
        page.locator(useThisAddressBtn).click();
    }
}
