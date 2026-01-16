package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.AddressModal;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.CheckoutPage;
import com.mycompany.app.pages.CustomizeGiftModal;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.PersonalizeItemModal;
import com.mycompany.app.pages.ProductPage;
import com.mycompany.app.pages.SignInPage;
import com.mycompany.app.pages.StagingLoginPage;

public class ProductPersonalizationTests extends TestBase {

    @Test
    void personalizeItem() throws InterruptedException {
        StagingLoginPage lp = new StagingLoginPage(page);
        ProductPage pp = new ProductPage(page);
        PersonalizeItemModal modal = new PersonalizeItemModal(page);
        CustomizeGiftModal customModal = new CustomizeGiftModal(page);
        HomePage hp = new HomePage(page);
        CartPage cartPage = new CartPage(page);
        SignInPage signInPage = new SignInPage(page);
        AddressModal shippingModal = new AddressModal(page);
        CheckoutPage checkoutPage = new CheckoutPage(page);

        String username = getProperty("username");
        String password = getProperty("password");
        String loginUrl = getProperty("baseUrl");
        String testEmail = getProperty("test_email");
        String testPassword = getProperty("test_password");
        String couponCode = getProperty("sale_item_coupon_code");
        String cardType = getProperty("card_type");
        String cardName = getProperty("name");
        String cardNumber = getProperty("card_number");
        String securityCode = getProperty("card_security_code");
        String cardExpMonth = getProperty("card_exp_month");
        String cardExpYear = getProperty("card_exp_year");
        String stockingUrl = "https://staging-www.personalizationmall.com/Winter-Wonderland-Personalized-Christmas-Stockings-p30508.prod?sdest=Search&sdestid=179238038";

        String threadColor = "Lilac";
        String font = "Frunch";
        String name = "QATest";

        page.navigate(loginUrl);
        lp.login(username, password);
        lp.closePopUp();

        page.navigate(stockingUrl);
        pp.click("button#personalizeBtn");

        modal.fillPersonalizationAndAddToCart(threadColor, font, name);
        customModal.selectClassicGiftBox();
        customModal.clickContinue();

        hp.clickViewCart();
        cartPage.updateQuantityAndVerifyTotal(3);
        cartPage.clickProceedToCheckout();

        signInPage.signIn(testEmail, testPassword);

        shippingModal.selectFirstAddressAndShip();
        shippingModal.click("input#ctl00_belowHeader_saveContinueBtn");

        checkoutPage.applyCoupon(couponCode);
        checkoutPage.enterPaymentInformation(cardType, cardName, cardNumber, securityCode, cardExpMonth, cardExpYear);
        checkoutPage.placeOrder();
    }
}
