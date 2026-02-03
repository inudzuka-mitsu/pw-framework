package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {
    
    public HomePage(Page page) {
        super(page); 
    }

    private final String searchBar = "#searchBox";
    private final String viewCartButton = ".block__added-to-cart a[href*='Cart.aspx']";
    private final String checkoutBtn = ".block__added-to-cart a[href*='Checkout.aspx']";
    private final String addedToCartHeader = "#ctl00_mainContent_itemAddedToCart_txtTitle";
    private final String personalizationDetails = "#ctl00_mainContent_itemAddedToCart_txtAddToCartPers, #ctl00_belowHeader_itemUpdatedInfoControl_txtAddToCartPers";

    private final String forHerNavLink = ".nav-list > .nav-item > a:text-is('For Her')";

    public void typeProduct(String productName) {
       page.locator(searchBar).click();
       page.locator(searchBar).fill(productName);
    }

    public void clickForHer() {
        page.locator(forHerNavLink).click();
    }

    public void searchProduct() {
        page.locator(searchBar).press("Enter");
    }

    public void clickViewCart() {
        page.locator(viewCartButton).click(new Locator.ClickOptions().setForce(true));
    }

    public void clickCheckout() {
        page.locator(checkoutBtn).click(new Locator.ClickOptions().setForce(true));
    }

    public void validateAddedToCartVisible() {
        assertThat(page.locator(addedToCartHeader)).isVisible();
        assertThat(page.locator(addedToCartHeader)).hasText("Added To Cart");
    }

    public void validatePersonalization(String color, String font, String name) {
        Locator details = page.locator(personalizationDetails);
        if (color != null && !color.isEmpty()) {
            assertThat(details).containsText(color);
        }

        if (font != null && !font.isEmpty()) {
            assertThat(details).containsText(font);
        }

        if (name != null && !name.isEmpty()) {
            assertThat(details).containsText(name);
        }
    }
}