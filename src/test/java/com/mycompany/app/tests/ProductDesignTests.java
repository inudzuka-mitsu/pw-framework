package com.mycompany.app.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.CheckoutPage;
import com.mycompany.app.pages.DesignMugPage;
import com.mycompany.app.pages.DesignToolPage;
import com.mycompany.app.pages.EditorPage;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.OrderConfirmationPage;
import com.mycompany.app.pages.ProductPage;
import com.mycompany.app.pages.login.SignInPage;
import com.mycompany.app.pages.login.StagingLoginPage;
import com.mycompany.app.pages.modals_popups.AddressModal;
import com.mycompany.app.pages.modals_popups.DesignPopup;
import com.mycompany.app.pages.modals_popups.PersonalizeItemModal;

public class ProductDesignTests extends TestBase {

    private StagingLoginPage stagingLoginPage;
    private ProductPage productPage;
    private DesignToolPage designToolPage;
    private EditorPage editorPage;
    private DesignPopup popup;
    private HomePage homePage;
    private SignInPage signInPage;
    private AddressModal shippingPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage confirmationPage;
    private DesignMugPage designMugPage;
    private PersonalizeItemModal personalizeModal;
    private CartPage cartPage;

    @Test
    @DisplayName("Verify user can design a wedding photo book and complete checkout")
    void designItem() {
        String PRODUCT_URL = getProperty("baseUrl") + "/Our-Wedding-Chronicle-Personalized-Photo-Book-p59192.prod?sdest=dept&sdestid=2787&storeid=77&categoryid=2787";
        String testEmail = getProperty("test_email_2");
        String testPassword = getProperty("test_password_2");
        String cardType = getProperty("card_type");
        String cardName = getProperty("name");
        String cardNumber = getProperty("card_number");
        String securityCode = getProperty("card_security_code");
        String cardExpMonth = getProperty("card_exp_month");
        String cardExpYear = getProperty("card_exp_year");
        
        stagingLoginPage = new StagingLoginPage(page);
        productPage = new ProductPage(page);
        designToolPage = new DesignToolPage(page);
        editorPage = new EditorPage(page);
        popup = new DesignPopup(page);
        homePage = new HomePage(page, isMobile());
        signInPage = new SignInPage(page);
        shippingPage = new AddressModal(page);
        checkoutPage = new CheckoutPage(page);
        confirmationPage = new OrderConfirmationPage(page);

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        page.navigate(PRODUCT_URL);
        productPage.clickPersonalizeBtn();

        designToolPage.clickGoStraightToEditor();
        editorPage.clickAddToCart();

        popup.handleValidationPopup();

        homePage.clickCheckout();

        signInPage.signIn(testEmail, testPassword);

        shippingPage.selectFirstAddressAndShip();
        shippingPage.clickSaveAndContinue();

        checkoutPage.enterPaymentInformation(cardType, cardName, cardNumber, securityCode, cardExpMonth, cardExpYear);
        checkoutPage.placeOrder();

        confirmationPage.verifyOrderSuccessMessage();
    }

    @Test
    @DisplayName("Verify user can change shipping address during checkout")
    void changeShippingAddress() {
        String PRODUCT_URL = getProperty("baseUrl") + "/Our-Wedding-Chronicle-Personalized-Photo-Book-p59192.prod?sdest=dept&sdestid=2787&storeid=77&categoryid=2787";
        String testEmail = getProperty("test_email_2");
        String testPassword = getProperty("test_password_2");
        
        stagingLoginPage = new StagingLoginPage(page);
        productPage = new ProductPage(page);
        designToolPage = new DesignToolPage(page);
        editorPage = new EditorPage(page);
        popup = new DesignPopup(page);
        homePage = new HomePage(page, isMobile());
        signInPage = new SignInPage(page);
        shippingPage = new AddressModal(page);
        checkoutPage = new CheckoutPage(page);
        confirmationPage = new OrderConfirmationPage(page);

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        page.navigate(PRODUCT_URL);
        productPage.clickPersonalizeBtn();

        designToolPage.clickGoStraightToEditor();
        editorPage.clickAddToCart();

        popup.handleValidationPopup();

        homePage.clickCheckout();

        signInPage.signIn(testEmail, testPassword);

        String firstSelectedAddress = shippingPage.selectFirstAddressAndReturnText();
        System.out.println(firstSelectedAddress);
        shippingPage.clickSaveAndContinue();
        checkoutPage.validateShippingAddress(firstSelectedAddress);

        checkoutPage.clickChangeShippingAddress();

        String secondAddress = shippingPage.selectSecondAddressAndReturnText();
        shippingPage.clickSaveAndContinue();
        checkoutPage.validateShippingAddress(secondAddress);
    }

    @Test
    @DisplayName("Verify user can design a coffee mug")
    void designMug() {
        String PRODUCT_URL = getProperty("baseUrl") + "/Design-Your-Own-Personalized-Coffee-Mug-11oz-White-i39099.item?productid=14671";

        designMugPage = new DesignMugPage(page);
        stagingLoginPage = new StagingLoginPage(page);
        productPage = new ProductPage(page);
        personalizeModal = new PersonalizeItemModal(page);
        homePage = new HomePage(page, isMobile());
        cartPage = new CartPage(page, isMobile());

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        page.navigate(PRODUCT_URL);

        productPage.validateDefaultHandleColor("White Handle");
        productPage.clickStartDesigning();

        designMugPage.clickSkip();
        designMugPage.clickProceed();

        personalizeModal.selectNoGiftBox();
        personalizeModal.checkPersonalizationCorrect();
        personalizeModal.clickAddToCart();

        homePage.clickViewCart();

        cartPage.validateProductInCart("Design Your Own Personalized Coffee Mug- 11oz. White");
    }
}
