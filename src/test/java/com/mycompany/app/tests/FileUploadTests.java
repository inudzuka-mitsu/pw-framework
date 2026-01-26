package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.CartPage;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.ProductPage;
import com.mycompany.app.pages.login.StagingLoginPage;
import com.mycompany.app.pages.modals_popups.PersonalizeItemModal;
import com.mycompany.app.pages.modals_popups.PhotoUploadModal;


public class FileUploadTests extends TestBase {

    private StagingLoginPage stagingLoginPage;
    private ProductPage productPage;
    private PhotoUploadModal photoUploadModal;
    private PersonalizeItemModal personalizeModal;
    private CartPage cartPage;
    private HomePage homePage;

    @Test
    void uploadPhotoVideo() {

        stagingLoginPage = new StagingLoginPage(page);
        productPage = new ProductPage(page);
        photoUploadModal = new PhotoUploadModal(page);
        personalizeModal = new PersonalizeItemModal(page);
        cartPage = new CartPage(page);
        homePage = new HomePage(page);

        String PRODUCT_URL = getProperty("baseUrl") + "/Photo-Personalized-Keyring-with-Video-QR-Code-i164699.item?productid=59112&sdest=Search&sdestid=179337628";
        String photoPath = System.getProperty("user.dir") + "/src/test/resources/jpegsystems-home.jpg";
        String videoPath = System.getProperty("user.dir") + "/src/test/resources/flower_video.mp4";

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();

        page.navigate(PRODUCT_URL);
        productPage.clickPersonalizeBtn();

        photoUploadModal.uploadPhoto(photoPath);
        photoUploadModal.validatePhotoUploaded();

        // photoUploadModal.uploadVideo(videoPath);
        // photoUploadModal.validateVideoIsUploaded();

        // personalizeModal.clickAddToCart();

        // homePage.clickViewCart();

        // cartPage.validateProductInCart("Personalized Photo Keyring with Video QR Code");

        page.pause();

    }

}
