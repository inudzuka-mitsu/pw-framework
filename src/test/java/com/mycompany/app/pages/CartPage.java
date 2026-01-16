package com.mycompany.app.pages;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
}