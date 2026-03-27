package com.mycompany.app.pages;

import java.nio.file.Paths; // <--- NEW IMPORT REQUIRED

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class PhotoEditorPage extends BasePage {

    public PhotoEditorPage(Page page) {
        super(page);
    }

    private final String editorIframe = "iframe[name='pmallmodaliframe']";
    private final String categoryDropdown = "select[name='message-library-category']"; 
    private final String libraryMessageList = ".message-library-content li"; 
    private final String photoTab = "//button[contains(text(), 'Photos')]"; 
    private final String addPhotoLaterBtn = "text=Add Photos Later";
    private final String libraryPhotoSource = ".LibraryPhotosList .PhotoPreview";
    private final String continueBtn = "button:has-text('Continue')";
    
    private final String productPhotoSlot = ".PhotoSlot";

    private final String uploadMyDeviceBtn = ".UploadLocalFilesButton"; 
    private final String addToCartBtn = "#ctl00_mainContent_addToCart_addToCartButton";

    private final String addToCartContainer = ".OrderButtonContainer";

    private FrameLocator getEditorFrame() {
        return page.frameLocator(editorIframe);
    }

    public void selectCategory(String categoryName) {
        System.out.println("Selecting category: " + categoryName);
        getEditorFrame().locator(categoryDropdown)
            .selectOption(new SelectOption().setLabel(categoryName));
    }

    public void selectFirstLibraryMessage() {
        System.out.println("Selecting the first message from the library...");
        Locator listItems = getEditorFrame().locator(libraryMessageList);
        listItems.first().waitFor();

        if (listItems.count() > 0) {
            listItems.first().click();
        } else {
            throw new RuntimeException("Library list is empty!");
        }
    }

    public void clickPhotoTab() {
        System.out.println("Switching to Photo Tab...");
        getEditorFrame().locator(photoTab).click();
    }

    public void clickAddPhotoLater() {
        System.out.println("Clicking 'Add Photo Later'...");
        Locator btn = getEditorFrame().locator(addPhotoLaterBtn);
        btn.waitFor();
        btn.click();
    }

    public void uploadPhoto(String absoluteFilePath) {
        System.out.println("Uploading photo from: " + absoluteFilePath);
        
        FileChooser fileChooser = page.waitForFileChooser(() -> {
            getEditorFrame().locator(uploadMyDeviceBtn).click();
        });
        fileChooser.setFiles(Paths.get(absoluteFilePath));
        
        System.out.println("Photo uploaded successfully.");
    }

    public void dragPhotoToSlot() {
        System.out.println("Dragging photo to slot...");
        Locator source = getEditorFrame().locator(libraryPhotoSource).first();
        Locator target = getEditorFrame().locator(productPhotoSlot).first();
        getEditorFrame().locator(".PhotoPreview.isLoaded").first().waitFor();

        source.dragTo(target);
        
        System.out.println("Photo dragged successfully.");
    }

    public void clickAddToCart() {
        System.out.println("Clicking 'Add To Cart'...");

        getEditorFrame().locator(addToCartContainer + ":not(.isDisabled)").waitFor();
        getEditorFrame().locator(addToCartBtn).click();
        
        System.out.println("Add to Cart clicked.");
    }
    
    public void clickContinue() {
        System.out.println("Clicking 'Continue'...");

        Locator btn = getEditorFrame().locator("a#cmdAddonGiftBoxV3");
        btn.waitFor();
        btn.click();
    }
}