package com.mycompany.app.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CustomizeGiftModal extends BasePage {

    private final FrameLocator modalFrame;

    public CustomizeGiftModal(Page page) {
        super(page);
        this.modalFrame = page.frameLocator("#pmallmodaliframe");
    }

    public void selectClassicGiftBox() {
        modalFrame.getByLabel("Classic gift box")
                  .check(new Locator.CheckOptions().setForce(true));
    }

    public void clickContinue() {
        modalFrame.getByText("Continue").click();
    }
    
}
