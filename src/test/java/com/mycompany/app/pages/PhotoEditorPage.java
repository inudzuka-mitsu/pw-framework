package com.mycompany.app.pages;

import java.nio.file.Paths;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class PhotoEditorPage extends BasePage {

    private final boolean isMobile;

    public PhotoEditorPage(Page page, boolean isMobile) {
        super(page);
        this.isMobile = isMobile;
    }

    // --- COMBINED LOCATORS ---
    
    // Looks for the desktop iframe name OR the mobile iframe ID
    private final String editorIframe = "iframe[name='pmallmodaliframe'], iframe#personalizationView";
    
    // Locators that exist on BOTH desktop and mobile
    private final String categoryDropdown = "select[name='message-library-category']"; 
    private final String libraryMessageList = ".message-library-content li, .msgSelection li"; 
    
    // Desktop-Specific Studio Locators
    private final String photoTab = "//button[contains(text(), 'Photos')]"; 
    private final String addPhotoLaterBtn = "button:has-text('Add Photos Later')";
    private final String libraryPhotoSource = ".LibraryPhotosList .PhotoPreview";
    private final String productPhotoSlot = ".PhotoSlot";
    private final String uploadMyDeviceBtn = ".UploadLocalFilesButton"; 
    private final String addToCartContainer = ".OrderButtonContainer";
    private final String desktopAddToCartBtn = "#ctl00_mainContent_addToCart_addToCartButton";
    private final String desktopContinueBtn = "a#cmdAddonGiftBoxV3";

    // Mobile-Specific Form Locators
    private final String studioUploadBtn = ".UploadLocalFilesButton, .EmptyBottomLibraryCTAButton";
    private final String mobileAddToCartBtn = "#addToCartLink, #submitButton";
    private final String mobileSelectPhotoBtn = ".photo-upload-btn, .select_photo_button__pdp-upload";

    // --- ACTIONS ---

    private FrameLocator getEditorFrame() {
        return page.frameLocator(editorIframe);
    }

    public void selectCategory(String categoryName) {
        System.out.println("Selecting category: " + categoryName);
        Locator dropdown = getEditorFrame().locator(categoryDropdown).first();
        dropdown.scrollIntoViewIfNeeded();
        dropdown.selectOption(new SelectOption().setLabel(categoryName));
    }

    public void selectFirstLibraryMessage() {
        System.out.println("Selecting the first message from the library...");
        Locator listItems = getEditorFrame().locator(libraryMessageList);
        listItems.first().waitFor();

        if (listItems.count() > 0) {
            listItems.first().scrollIntoViewIfNeeded();
            listItems.first().click(new Locator.ClickOptions().setForce(true));
        } else {
            throw new RuntimeException("Library list is empty!");
        }
    }

    public void clickPhotoTab() {
        if (!isMobile) {
            System.out.println("Switching to Photo Tab...");
            Locator tab = getEditorFrame().locator(photoTab).first();
            tab.waitFor();
            tab.click(new Locator.ClickOptions().setForce(true));
        } else {
            Locator btn = getEditorFrame().locator("button.PhotoSlot").first();
            btn.click(new Locator.ClickOptions().setForce(true));
        }
    }

    public void clickAddPhotoLater() {
    
            System.out.println("Clicking 'Add Photo Later'...");
            Locator btn = getEditorFrame().locator(addPhotoLaterBtn).first();
            btn.waitFor();
            btn.click(new Locator.ClickOptions().setForce(true));
        
    }

    public void uploadPhoto(String absoluteFilePath) {
        System.out.println("Uploading photo from: " + absoluteFilePath);
        
        Locator legacyMobileBtn = getEditorFrame().locator(mobileSelectPhotoBtn).first();
        
        // 1. Check if the product is using the OLD mobile form editor
        if (isMobile && legacyMobileBtn.isVisible()) {
            System.out.println("Detected legacy mobile form. Opening crop iframe...");
            legacyMobileBtn.click(new Locator.ClickOptions().setForce(true));
        } 
        // 2. Otherwise, we are in the Studio Editor (Desktop OR Mobile)!
        else {
            System.out.println("Detected Studio Editor. Launching file chooser...");
            FileChooser fileChooser = page.waitForFileChooser(() -> {
                // Using .last() safely bypasses any hidden background elements
                Locator btn = getEditorFrame().locator(studioUploadBtn).last();
                btn.scrollIntoViewIfNeeded();
                btn.click(new Locator.ClickOptions().setForce(true));
            });
            fileChooser.setFiles(Paths.get(absoluteFilePath));
        }
        
        System.out.println("Photo uploaded successfully.");
    }

    public void dragPhotoToSlot() {
        if (!isMobile) {
            System.out.println("Dragging photo to slot...");
            Locator source = getEditorFrame().locator(libraryPhotoSource).last();
            Locator target = getEditorFrame().locator(productPhotoSlot).first();
            
            // Wait for the image preview to fully load in the sidebar before trying to drag it
            getEditorFrame().locator(".PhotoPreview.isLoaded").last().waitFor();

            source.dragTo(target);
            System.out.println("Photo dragged successfully.");
        } else {
            System.out.println("Mobile flow: Skipping drag-and-drop (not supported in mobile form view).");
        }
    }

    public void clickAddToCart() {
        System.out.println("Clicking 'Add To Cart'...");

        if (isMobile) {
            Locator btn = getEditorFrame().locator(mobileAddToCartBtn).first();
            btn.scrollIntoViewIfNeeded();
            btn.click(new Locator.ClickOptions().setForce(true));
        } else {
            getEditorFrame().locator(addToCartContainer + ":not(.isDisabled)").waitFor();
            getEditorFrame().locator(desktopAddToCartBtn).click();
        }
        
        System.out.println("Add to Cart clicked.");
    }
    
    public void clickContinue() {
        if (!isMobile) {
            System.out.println("Clicking 'Continue'...");
            Locator btn = getEditorFrame().locator(desktopContinueBtn).first();
            btn.waitFor();
            btn.click(new Locator.ClickOptions().setForce(true));
        }
    }
}