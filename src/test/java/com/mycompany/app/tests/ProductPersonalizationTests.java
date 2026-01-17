package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.AddressModal;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.CheckoutPage;
import com.mycompany.app.pages.CustomizeGiftModal;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.OrderConfirmationPage;
import com.mycompany.app.pages.PersonalizeItemModal;
import com.mycompany.app.pages.ProductPage;
import com.mycompany.app.pages.SignInPage;
import com.mycompany.app.pages.StagingLoginPage;

public class ProductPersonalizationTests extends TestBase {

    private StagingLoginPage stagingLoginPage;
    private SignInPage signInPage;
    private ProductPage productPage;
    private PersonalizeItemModal personalizeModal;
    private CustomizeGiftModal giftBoxModal;
    private HomePage homePage;
    private CartPage cartPage;
    private AddressModal shippingPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage confirmationPage;

    private final String PRODUCT_URL = "https://staging-www.personalizationmall.com/Winter-Wonderland-Personalized-Christmas-Stockings-p30508.prod?sdest=Search&sdestid=179238038";
    private final String THREAD_COLOR = "Lilac";
    private final String FONT_STYLE = "Frunch";
    private final String PERSONALIZATION_TEXT = "QATest";
    private final int QUANTITY = 3;

    @Test
    void personalizeItem() throws InterruptedException {
        stagingLoginPage = new StagingLoginPage(page);
        signInPage = new SignInPage(page);
        productPage = new ProductPage(page);
        personalizeModal = new PersonalizeItemModal(page);
        giftBoxModal = new CustomizeGiftModal(page);
        homePage = new HomePage(page);
        cartPage = new CartPage(page);
        shippingPage = new AddressModal(page);
        checkoutPage = new CheckoutPage(page);
        confirmationPage = new OrderConfirmationPage(page);

        String testEmail = getProperty("test_email");
        String testPassword = getProperty("test_password");
        String couponCode = getProperty("sale_item_coupon_code");
        String cardType = getProperty("card_type");
        String cardName = getProperty("name");
        String cardNumber = getProperty("card_number");
        String securityCode = getProperty("card_security_code");
        String cardExpMonth = getProperty("card_exp_month");
        String cardExpYear = getProperty("card_exp_year");

        page.navigate(getProperty("baseUrl"));
        stagingLoginPage.login(getProperty("username"), getProperty("password"));
        stagingLoginPage.closePopUp();

        page.navigate(PRODUCT_URL);
        productPage.click("button#personalizeBtn");

        personalizeModal.fillPersonalizationAndAddToCart(THREAD_COLOR, FONT_STYLE, PERSONALIZATION_TEXT);
        giftBoxModal.selectClassicGiftBox();
        giftBoxModal.clickContinue();

        homePage.validateAddedToCartVisible();
        homePage.validatePersonalization(THREAD_COLOR, FONT_STYLE, PERSONALIZATION_TEXT);
        homePage.clickViewCart();
        cartPage.updateQuantityAndVerifyTotal(QUANTITY);
        cartPage.clickProceedToCheckout();

        signInPage.signIn(testEmail, testPassword);

        shippingPage.selectFirstAddressAndShip();
        shippingPage.click("input#ctl00_belowHeader_saveContinueBtn");

        checkoutPage.applyCoupon(couponCode);
        checkoutPage.enterPaymentInformation(cardType, cardName, cardNumber, securityCode, cardExpMonth, cardExpYear);
        checkoutPage.placeOrder();

        confirmationPage.verifyOrderSuccessMessage();
    }
}
