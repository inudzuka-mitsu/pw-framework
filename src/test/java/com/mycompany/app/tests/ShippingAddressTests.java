package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.AccountPage;
import com.mycompany.app.pages.AddAddressPage;
import com.mycompany.app.pages.AddressModal;
import com.mycompany.app.pages.SignInPage;


public class ShippingAddressTests extends TestBase {
    
    private AddAddressPage addAddressPage;
    private SignInPage signInPage;
    private AccountPage accountPage;
    private AddressModal shippingPage;

    @Test
    void addShippingAddress() {
        signInPage = new SignInPage(page);
        addAddressPage = new AddAddressPage(page);
        shippingPage = new AddressModal(page);
        accountPage = new AccountPage(page);

        String testEmail = getProperty("test_email_2");
        String testPassword = getProperty("test_password_2");

        String addressNickname = "QA Test " + System.currentTimeMillis();
        String firstName = "Zhibek";
        String lastName = "Amankulova";
        String phoneNumber = "3125550199";
        String streetAddress = "123 W Madison St";
        String city = "Chicago";
        String state = "IL";
        String zipCode = "60602";

        page.navigate(getProperty("stagingBaseUrl"));
        page.navigate(getProperty("baseUrl") + "/Register.aspx?");
        signInPage.signIn(testEmail, testPassword);

        accountPage.clickManageShippingAddress();
        shippingPage.clickAddNewAddress();
        
        addAddressPage.fillNewAddressFormAndSubmit(
            addressNickname,
            firstName,
            lastName,
            phoneNumber,
            streetAddress,
            city,
            state,
            zipCode
        );

        page.navigate(getProperty("baseUrl") + "/AddressBook.aspx");
        shippingPage.validateLastAddress(addressNickname,
            firstName,
            lastName,
            phoneNumber,
            streetAddress,
            city,
            state,
            zipCode);
    }
}
