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

    public void validateItemSearchResults(String searchQuery) {
        Locator items = page.locator(itemDescription);
        Locator itemImages = page.locator(itemImage);

        assertEquals(items.count(), itemImages.count());

        for (int i = 0; i < items.count(); i++) {
           String actualText = items.nth(i).innerText().toLowerCase();
           String imageSource = items.nth(i).innerText().toLowerCase();

           assertTrue(actualText.contains(searchQuery.toLowerCase()), 
            "Expected product item at index " + i + " to contain '" + searchQuery + "', but got: " + actualText);
           assertTrue(itemImages.nth(i).isVisible());

           assertTrue(imageSource.contains(searchQuery.toLowerCase()), 
            "Expected image of a product item at index " + i + " to contain '" + searchQuery + "', but got: " + actualText);
        }
    }

}
