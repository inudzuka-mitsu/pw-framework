package com.mycompany.app.pages;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CartPage extends BasePage {

    public CartPage(Page page) {
        super(page);
    }

    private final String quantityInput = "input.inp__qty-title";
    private final String updateButton = "input.btn__qty-update";
    private final String itemPriceText = ".li__item-price b";
    private final String boxPriceText = ".li__gift-box span.reg";
    private final String totalPriceText = ".li__item-total .sp__amt-total";
    private final String proceedToCheckoutBtn = "a.begin-checkout:has-text('Proceed To Checkout')";

    private final String saveForLaterLink = "ul.list__prev-edit a:has-text('Save for later')";
    private final String editLink = "ul.list__prev-edit a:has-text('Edit')";
    
    private final String moveToCartLink = "#ctl00_mainContent_savedItemsList .block__saveto-cart .moveSavedItem";
    private final String savedItemsContainer = "#ctl00_mainContent_savedItemsList";
    private final String emptyCartContainer = "#ctl00_mainContent_cartEmpty";
    private final String savedNotificationText = "#ctl00_mainContent_orderItemsSavedContent2019_notificationsList li";

    public double getItemPrice() {
        return parsePrice(page.locator(itemPriceText).innerText());
    }

    public double getBoxPrice() {
        if (page.locator(boxPriceText).isVisible()) {
            return parsePrice(page.locator(boxPriceText).innerText());
        }
        return 0.00;
    }

    public void clickProceedToCheckout() {
        page.locator(proceedToCheckoutBtn).click(new Locator.ClickOptions().setForce(true));
    }

    public void updateQuantityAndVerifyTotal(int newQuantity) {
        double itemPrice = getItemPrice();
        double boxPrice = getBoxPrice();
    
        double unitCost = itemPrice + boxPrice;
    
        double expectedTotal = unitCost * newQuantity;
        String expectedTotalStr = String.format("%.2f", expectedTotal);

        System.out.println("--- Cart Calculation Check ---");
        System.out.println("New Quantity: " + newQuantity);
        System.out.println("Expected Total: $" + expectedTotalStr);

        page.locator(quantityInput).fill(String.valueOf(newQuantity));
        page.locator(updateButton).click(new Locator.ClickOptions().setForce(true));

        String safeTotal = expectedTotalStr.replace(".", "\\.");
        Pattern pricePattern = Pattern.compile(".*\\$" + safeTotal + ".*");
        
        assertThat(page.locator(totalPriceText)).hasText(pricePattern);
    }

    private double parsePrice(String priceText) {
        if (priceText == null || priceText.isEmpty()) return 0.0;
        return Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));
    }

    public void clickSaveForLater() {
        Locator saveLink = page.locator(saveForLaterLink).first();

        try {
            saveLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(5000));
            saveLink.scrollIntoViewIfNeeded();
            try {
                saveLink.click(new Locator.ClickOptions().setForce(true).setTimeout(2000));
            } catch (Exception clickError) {
                System.out.println("Standard click intercepted by overlay. Attempting JS click...");
                saveLink.dispatchEvent("click");
            }

        } catch (Exception e) {
            System.out.println("Failed to click 'Save for later'. Error: " + e.getMessage());
        }
    }

    public void clickEdit() {
        Locator saveLink = page.locator(editLink).first();

        try {
            saveLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(5000));
            saveLink.scrollIntoViewIfNeeded();
            try {
                saveLink.click(new Locator.ClickOptions().setForce(true).setTimeout(2000));
            } catch (Exception clickError) {
                System.out.println("Standard click intercepted by overlay. Attempting JS click...");
                saveLink.dispatchEvent("click");
            }

        } catch (Exception e) {
            System.out.println("Failed to click 'Edit' link. Error: " + e.getMessage());
        }
    }

    public void clickMoveToCart() {
        Locator moveLink = page.locator(moveToCartLink).first();
        
        try {
            moveLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
            moveLink.scrollIntoViewIfNeeded();
            try {
                moveLink.click(new Locator.ClickOptions().setForce(true).setTimeout(2000));
            } catch (Exception clickError) {
                System.out.println("Standard click on 'Move To Cart' intercepted. Attempting JS click...");
                moveLink.dispatchEvent("click");
            }
        } catch (Exception e) {
            System.out.println("Failed to click 'Move To Cart'. Error: " + e.getMessage());
        }
    }

    public void validateProductInSavedForLater(String productName) {
        Locator container = page.locator(savedItemsContainer);
        container.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Locator firstSavedProductTitle = container.locator(".block__saveto-cart").first().locator("h3 a");
        assertThat(firstSavedProductTitle).containsText(productName);
    }

    public void validateEmptyCartAndSavedMessage() {
        Locator emptyContainer = page.locator(emptyCartContainer);
        assertThat(emptyContainer).isVisible();

        assertThat(emptyContainer).containsText("Currently, there are no items in your shopping cart!");
        assertThat(emptyContainer).containsText("Return to our Home Page to find the perfect, personalized gift!");

        assertThat(emptyContainer.locator("a:has-text('Continue Shopping')")).isVisible();
        assertThat(emptyContainer.locator("a:has-text('Homepage')")).isVisible();
        
        Locator notification = page.locator(savedNotificationText);
        assertThat(notification).isVisible();
        assertThat(notification).containsText("Requested item has been put into saved items list");
    }

    public void validateProductInCart(String productName) {
        Locator cartItemTitle = page.locator(".block__shopping-cart h3 a")
                                    .filter(new Locator.FilterOptions().setHasText(productName)).first();
        assertThat(cartItemTitle).isVisible(
            new LocatorAssertions.IsVisibleOptions().setTimeout(30000)
        );
    }

    public void validateProductAddedToCart(String productName) {
        Locator cartItemTitle = page.locator(".block.block__added-to-cart #ctl00_mainContent_itemAddedToCart_txtAddToCartProduct")
                                    .filter(new Locator.FilterOptions().setHasText(productName)).first();
        assertThat(cartItemTitle).isVisible(
            new LocatorAssertions.IsVisibleOptions().setTimeout(30000)
        );
    }
}