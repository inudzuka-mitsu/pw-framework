package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.CelebrationsPassportPage;
import com.mycompany.app.pages.login.StagingLoginPage;
import com.mycompany.app.pages.modals_popups.Footer;

public class PassportTests extends TestBase {

    private CartPage cartPage;
    private Footer footer;
    private StagingLoginPage stagingLoginPage;
    private CelebrationsPassportPage passportPage;

    @Test
    void celebrationsPassport() {

        footer = new Footer(page);
        cartPage = new CartPage(page);
        stagingLoginPage = new StagingLoginPage(page);
        passportPage = new CelebrationsPassportPage(page);

        String ITEM_PRICE = "$19.99";

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        footer.clickCelebrationsPassport();

        passportPage.verifyPassportPageLoaded();
        passportPage.validatePriceOnButton(ITEM_PRICE);
        passportPage.clickSignUp();

        cartPage.validateProductInCart("Passport Membership");
    }
}
