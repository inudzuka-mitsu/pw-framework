package com.mycompany.app.pages.modals_popups;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.mycompany.app.pages.BasePage;

public class DesignPopup extends BasePage {

    public DesignPopup(Page page) {
        super(page);
    }

    private final String iframeSelector = "#app_iframe"; 
    private final String titleText = ".popup-title-row";
    private final String subtitleText = ".popup-subtitle-row";
    private final String descriptionText = ".popup-description-row";
    private final String confirmationCheckboxLabel = "span[data-sid='validationPopupCheckBox'] label"; 
    private final String proceedButton = "button[data-sid='popupDrawerPrimaryButton']";

    public void handleValidationPopup() {
        System.out.println(">>> Handling validation popup...");
        FrameLocator frame = page.frameLocator(iframeSelector);
        
        frame.locator(titleText).waitFor();

        assertThat(frame.locator(titleText)).hasText("Oops!");
        assertThat(frame.locator(subtitleText)).hasText("Your project contains some minor flaws.");
        assertThat(frame.locator(descriptionText)).containsText("Please review them carefully before ordering.");
        
        frame.locator(confirmationCheckboxLabel).click();
        
        assertThat(frame.locator(proceedButton)).isEnabled();
        frame.locator(proceedButton).click();
        
        System.out.println(">>> Validation popup cleared.");
    }
}
