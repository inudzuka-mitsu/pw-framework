package com.mycompany.app.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductCatalogPage extends BasePage {

    public ProductCatalogPage(Page page) {
       super(page);
    }

    String itemDescription = "div.search-item div.thumbProduct";
    String itemImage = "div.search-item a img";
    String currentSelectionItem = "#leftNavCommon div.search-terms";
    String productLink = "div.search-item a";

    public void validateCurrentSelection(String searchQuery) {
        assertEquals(searchQuery.toLowerCase(), page.locator(currentSelectionItem).innerText().toLowerCase());
    }

    public void validateItemSearchResults(String searchQuery) {
        Locator items = page.locator(itemDescription);
        Locator itemImages = page.locator(itemImage);

        assertEquals(items.count(), itemImages.count(), "Mismatch between count of descriptions and images.");

    
        String rawQuery = searchQuery.toLowerCase();
        String validatedQuery = rawQuery.endsWith("s") ? 
                            rawQuery.substring(0, rawQuery.length() - 1) : 
                            rawQuery;

        for (int i = 0; i < items.count(); i++) {
            String actualDescription = items.nth(i).innerText().toLowerCase();
            String imageAltText = itemImages.nth(i).getAttribute("alt");
            String imageTitleText = itemImages.nth(i).getAttribute("title");
            String imageMetadata = (imageAltText + " " + imageTitleText).toLowerCase();

            assertTrue(actualDescription.contains(validatedQuery), 
            String.format("Expected item description at index %d to contain '%s', but found: '%s'", 
            i, validatedQuery, actualDescription));

            assertTrue(imageMetadata.contains(validatedQuery), 
            String.format("Expected image Alt/Title at index %d to contain '%s'. \nFound Alt: '%s' \nFound Title: '%s'", 
            i, validatedQuery, imageAltText, imageTitleText));
       }
    }

    public void clickFirstProduct() {
    Locator firstProduct = page.locator(productLink).first();
    
    firstProduct.hover();
    page.waitForTimeout(500);
   
    firstProduct.click();
    }
}