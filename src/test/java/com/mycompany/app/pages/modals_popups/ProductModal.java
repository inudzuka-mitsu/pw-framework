package com.mycompany.app.pages.modals_popups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mycompany.app.pages.BasePage;

public class ProductModal extends BasePage{

    public ProductModal(Page page) {
        super(page);
    }

    String categoryName = "div.ea-sug-category-name";
    String productItem = "div.ea-sug-product-name";
    String productSuggestions = "div[class='ea-sug-section'] ul li";

    public void validateCategoryName(String expected) {
        String actualText = page.locator(categoryName).innerText();
        assertEquals("Shop " + expected + " Category", actualText);
    }

    public void validateAllProductsContainProductName(String product) {
        Locator items = page.locator(productItem);
        
        String expectedTerm = product.toLowerCase();
        String expectedSingular = expectedTerm.endsWith("s") ? expectedTerm.substring(0, expectedTerm.length() - 1) : expectedTerm;
    
        for (int i = 0; i < items.count(); i++) {
           String actualText = items.nth(i).innerText().toLowerCase();
           boolean containsMatch = actualText.contains(expectedTerm) || actualText.contains(expectedSingular);
           
           assertTrue(containsMatch, 
            "Expected product item at index " + i + " to contain '" + expectedTerm + "' or '" + expectedSingular + "', but got: " + actualText);
        }
    }

    public void validateAllProductSuggestionsContainProductName(String product) {
        Locator items = page.locator(productSuggestions);
        
        String expectedTerm = product.toLowerCase();
        String expectedSingular = expectedTerm.endsWith("s") ? expectedTerm.substring(0, expectedTerm.length() - 1) : expectedTerm;
    
        for (int i = 0; i < items.count(); i++) {
           String actualText = items.nth(i).innerText().toLowerCase();
           boolean containsMatch = actualText.contains(expectedTerm) || actualText.contains(expectedSingular);
           
           assertTrue(containsMatch, 
            "Expected product item at index " + i + " to contain '" + expectedTerm + "' or '" + expectedSingular + "', but got: " + actualText);
        }
    }
}
