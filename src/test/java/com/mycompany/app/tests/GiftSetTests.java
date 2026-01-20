package com.mycompany.app.tests;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.PersonalizeItemModal;
import com.mycompany.app.pages.ProductPage;
import com.mycompany.app.pages.SignInPage;
import com.mycompany.app.pages.StagingLoginPage;

public class GiftSetTests extends TestBase {

    private StagingLoginPage stagingLoginPage;
    private ProductPage productPage;
    private PersonalizeItemModal personalizeModal;
    private HomePage homePage;
    private CartPage cartPage;
    private SignInPage signInPage;

    private final String PRODUCT_SLUG = "/Whiskey-Glass-Decanter-Personalized-Gift-Set-Lavish-Last-Name-p55731.prod?sdest=store-one&sdestid=75";
    private final String PRODUCT_NAME = "Lavish Last Name Personalized Whiskey Glass & Decanter Gift Set";
    private final String PERSONALIZATION_MSG = "Happy Birthday!";

    @BeforeEach
    @Override
    public void setup() throws IOException {
        super.setup();
        stagingLoginPage = new StagingLoginPage(page);
        productPage = new ProductPage(page);
        personalizeModal = new PersonalizeItemModal(page);
        homePage = new HomePage(page);
        cartPage = new CartPage(page);
        signInPage = new SignInPage(page);
    }

    @Test
    @DisplayName("Verify user can personalize a gift set, save it for later and move it back to cart")
    void giftSetPersonalizationFlow() {
        String testEmail = getProperty("test_email_2");
        String testPassword = getProperty("test_password_2");

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        page.navigate(getProperty("baseUrl") + PRODUCT_SLUG);
        productPage.clickPersonalizeBtn();
        try {
            personalizeModal.fillGiftSetPersonalizationAndAddToCart("T", "name1"); 
            personalizeModal.fillGiftSetPersonalizationAndAddToCart("M", "name2"); 
            personalizeModal.fillGiftSetPersonalizationAndAddToCart("K", "name3"); 
            personalizeModal.fillGiftSetPersonalizationAndAddToCart("K", "name4"); 
            personalizeModal.fillGiftSetPersonalizationAndAddToCart("L", "name5"); 
            
            personalizeModal.selectColor("Blue");
            personalizeModal.clickContinue();
            
            personalizeModal.enterMessage(PERSONALIZATION_MSG);
            personalizeModal.clickContinue();
            
            personalizeModal.checkPersonalizationCorrect();
            personalizeModal.clickAddToCart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        homePage.clickViewCart();

        cartPage.clickSaveForLater();

        signInPage.signIn(testEmail, testPassword);

        cartPage.clickSaveForLater(); 

        cartPage.validateEmptyCartAndSavedMessage();
        cartPage.validateProductInSavedForLater(PRODUCT_NAME);

        cartPage.clickMoveToCart();
        cartPage.validateProductInCart(PRODUCT_NAME);
    }
}