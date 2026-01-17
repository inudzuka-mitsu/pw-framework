package com.mycompany.app.pages;

import java.util.regex.Pattern;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PersonalizeItemModal extends BasePage {

    private final FrameLocator modalFrame;

    public PersonalizeItemModal(Page page) {
        super(page);
        this.modalFrame = page.frameLocator("#pmallmodaliframe");
    }

    private final String threadColorDropdown = "div[data-select='pers159022']";
    private final String threadColorOptionPattern = "#ul_pers159022 li[data-val='%s']";

    private final String fontDropdown = "div[data-select='pers159021']";
    private final String fontOptionPattern = "#ul_pers159021 li[data-val='%s']";

    private final String productImage = "#productImage";

    private final String nameInput = "#ctl00_mainContent_pers_pers159019";
    private final String confirmCheckbox = "#checkConfirm";
    private final String addToCartBtn = "[name='ctl00$mainContent$addToCart$addToCartButton']";

    public void selectThreadColor(String color) {
        modalFrame.locator(threadColorDropdown).click();
        
        String optionLocator = String.format(threadColorOptionPattern, color);
        modalFrame.locator(optionLocator).click();
    }

    public void selectFont(String font) {
        modalFrame.locator(fontDropdown).click();
        
        String optionLocator = String.format(fontOptionPattern, font);
        modalFrame.locator(optionLocator).click();
    }

    public void enterName(String name) {
        modalFrame.locator(nameInput).fill(name);
        modalFrame.locator(nameInput).press("Tab");
    }

    public void checkPersonalizationCorrect() {
        modalFrame.locator(confirmCheckbox).check();
    }

    public void clickAddToCart() {
        modalFrame.locator(addToCartBtn).click();
    }

    public void fillPersonalizationAndAddToCart(String color, String font, String name) {
        selectThreadColor(color);
        selectFont(font);
        enterName(name);
        verifyPreviewImagePersonalization(color, font, name);
        checkPersonalizationCorrect();
        clickAddToCart();
    }

    public void verifyPreviewImagePersonalization(String color, String font, String name) {
        String safeColor = color.replace(" ", "+").replace("+", "\\+");
        String safeFont = font.replace(" ", "+").replace("+", "\\+");
        String safeName = name.replace(" ", "+").replace("+", "\\+");

        String regex = String.format(".*value1=%s.*value2=%s.*value3=%s.*", safeColor, safeFont, safeName);
        
        Pattern srcPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        assertThat(modalFrame.locator(productImage))
            .hasAttribute("src", srcPattern, 
                new LocatorAssertions.HasAttributeOptions().setTimeout(20000));
    }
}