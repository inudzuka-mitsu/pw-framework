package com.mycompany.app.pages.modals_popups;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.mycompany.app.pages.BasePage;

public class PersonalizeItemModal extends BasePage {

    private final String iframeSelector = "#pmallmodaliframe";

    public PersonalizeItemModal(Page page) {
        super(page);
    }

    private final String threadColorDropdown = "div[data-select='pers159022']";
    private final String threadColorOptionPattern = "#ul_pers159022 li[data-val='%s']";
    private final String fontDropdown = "div[data-select='pers159021']";
    private final String fontOptionPattern = "#ul_pers159021 li[data-val='%s']";
    private final String productImage = "#productImage";
    
    private final String addToCartBtn = "[name='ctl00$mainContent$addToCart$addToCartButton']";
    private final String continueButton = "a#cmdAddonGiftBox";
    
    private final String colorDropdownByLabel = "tr:has(.pers-title:has-text('Color')) + tr .dropdown-btn";
    private final String activeDropdownOptions = ".custom-dropdown ul.select-active li[data-val='%s']";

    private final String noGiftBoxRadio = "label:has-text('No Gift Box')";

    private Locator getLocator(String selector) {
        if (page.locator(iframeSelector).isVisible()) {
            return page.frameLocator(iframeSelector).locator(selector);
        } else {
            return page.locator(selector);
        }
    }

    public void fillInputByLabel(String labelText, String value) {
        String selector = String.format("tr:has(.pers-title:has-text('%s')) + tr input", labelText);
        Locator input = getLocator(selector);
        input.clear();
        input.fill(value);
        input.press("Tab");
    }

    public void selectThreadColor(String color) {
        getLocator(threadColorDropdown).click();
        String optionLocator = String.format(threadColorOptionPattern, color);
        getLocator(optionLocator).click();
    }

    public void selectFont(String font) {
        getLocator(fontDropdown).click();
        String optionLocator = String.format(fontOptionPattern, font);
        getLocator(optionLocator).click();
    }

    public void selectColor(String color) {
        getLocator(colorDropdownByLabel).click();
        String optionLocator = String.format(activeDropdownOptions, color);
        getLocator(optionLocator).click();
    }

    public void selectNoGiftBox() {
        getLocator(noGiftBoxRadio).click();
    }

    public void enterName(String name) {
        fillInputByLabel("Name", name);
    }

    public void enterMonogram(String monogram) {
        fillInputByLabel("Initial Monogram", monogram);
    }

    public void enterGiftSetName(String name) {
        fillInputByLabel("Name", name);
    }

    public void checkPersonalizationCorrect() {
        Locator realInput = getLocator("input#checkConfirm");
        Locator checkboxLabel = getLocator("label[for='checkConfirm']");

        try {
            System.out.println("Attempting to check confirm box...");

            checkboxLabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
            
            if (!realInput.isChecked()) {
                checkboxLabel.click();
            }
            
        } catch (Exception e) {
            System.out.println("Click failed. Forcing check via JavaScript.");
            realInput.evaluate("el => el.checked = true");
        }
    }

    public void clickContinue() {
        getLocator(continueButton).click();
    }

    public void clickAddToCart() {
        getLocator(addToCartBtn).click();
    }

    public void fillPersonalizationAndAddToCart(String color, String font, String name) {
        selectThreadColor(color);
        selectFont(font);
        enterName(name);
        verifyPreviewImagePersonalization(color, font, name);
        checkPersonalizationCorrect();
        clickAddToCart();
    }

    public void fillGiftSetPersonalizationAndAddToCart(String monogram, String name) throws InterruptedException {
        enterGiftSetName(name);
        enterMonogram(monogram);
        Thread.sleep(5000);
        verifyGiftSetPreviewImage(monogram, name);
        clickContinue();
        Thread.sleep(5000);
    }

    public void verifyPreviewImagePersonalization(String color, String font, String name) {
        List<String> activeParams = new ArrayList<>();
        
        if (color != null && !color.isEmpty()) activeParams.add(color);
        if (font != null && !font.isEmpty()) activeParams.add(font);
        if (name != null && !name.isEmpty()) activeParams.add(name);

        StringBuilder regexBuilder = new StringBuilder(".*");

        for (int i = 0; i < activeParams.size(); i++) {
            int valueIndex = i + 1; 
            String safeValue = escapeForRegex(activeParams.get(i));
            regexBuilder.append("value").append(valueIndex).append("=").append(safeValue).append(".*");
        }

        String finalRegex = regexBuilder.toString();
        System.out.println("Validating Image Src with Dynamic Regex: " + finalRegex);

        Pattern srcPattern = Pattern.compile(finalRegex, Pattern.CASE_INSENSITIVE);

        assertThat(getLocator(productImage))
            .hasAttribute("src", srcPattern, 
                new LocatorAssertions.HasAttributeOptions().setTimeout(20000));
    }

    private String escapeForRegex(String input) {
        return input.replace(" ", "+").replace("+", "\\+");
    }

    public void verifyGiftSetPreviewImage(String monogram, String name) {
        String safeMonogram = monogram.replace(" ", "+").replace("+", "\\+");
        String safeName = name.replace(" ", "+").replace("+", "\\+");

        String regex = String.format(".*value1=%s.*value2=%s.*", safeMonogram, safeName);
        Pattern srcPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        assertThat(getLocator(productImage))
            .hasAttribute("src", srcPattern, 
                new LocatorAssertions.HasAttributeOptions().setTimeout(20000));
    }

    public void enterMessage(String message) {
        fillTextAreaByLabel("Message", message); 
    }

    public void fillTextAreaByLabel(String labelText, String value) {
        String selector = String.format("tr:has(.pers-title:has-text('%s')) + tr textarea", labelText);
        Locator input = getLocator(selector);
        input.clear();
        input.fill(value);
        input.press("Tab");
    }
}