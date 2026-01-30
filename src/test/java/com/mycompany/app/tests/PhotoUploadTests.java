package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.CheckoutPage;
import com.mycompany.app.pages.DesignToolPage;
import com.mycompany.app.pages.EditorPage;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.OrderConfirmationPage;
import com.mycompany.app.pages.PhotoEditorPage;
import com.mycompany.app.pages.ProductPage;
import com.mycompany.app.pages.login.SignInPage;
import com.mycompany.app.pages.login.StagingLoginPage;
import com.mycompany.app.pages.modals_popups.AddressModal;
import com.mycompany.app.pages.modals_popups.DesignPopup;

public class PhotoUploadTests extends TestBase {

    private StagingLoginPage stagingLoginPage;
    private ProductPage productPage;
    private PhotoEditorPage photoEditorPage;
    private DesignToolPage designToolPage;
    private EditorPage editorPage;
    private DesignPopup popup;
    private HomePage homePage;
    private SignInPage signInPage;
    private AddressModal shippingPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage confirmationPage;
    private CartPage cartPage;

    @Test
    void photoEditor() {

        stagingLoginPage = new StagingLoginPage(page);
        productPage = new ProductPage(page);
        photoEditorPage = new PhotoEditorPage(page);
        designToolPage = new DesignToolPage(page);
        editorPage = new EditorPage(page);
        popup = new DesignPopup(page);
        homePage = new HomePage(page);
        signInPage = new SignInPage(page);
        shippingPage = new AddressModal(page);
        checkoutPage = new CheckoutPage(page);
        confirmationPage = new OrderConfirmationPage(page);

        String PRODUCT_URL = getProperty("baseUrl") + "/Family-Photo-Personalized-Coffee-Mugs-p25561.prod?sdest=dept&sdestid=2115&storeid=34&categoryid=2115";
        String photoPath = System.getProperty("user.dir") + "/src/test/resources/lake.jpg";

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        page.navigate(PRODUCT_URL);
        page.pause();
        productPage.clickPersonalizeBtn();

        photoEditorPage.clickAddPhotoLater();
        photoEditorPage.selectCategory("Anniversary");
        photoEditorPage.selectFirstLibraryMessage();
        photoEditorPage.clickPhotoTab();
        photoEditorPage.uploadPhoto(photoPath);
        photoEditorPage.clickPhotoTab();
        photoEditorPage.dragPhotoToSlot();
        photoEditorPage.clickAddToCart();
        photoEditorPage.clickContinue();

        homePage.clickViewCart();
        cartPage.validateProductInCart("Family Photo Personalized Coffee Mug 11 oz.- White");
    }

}
