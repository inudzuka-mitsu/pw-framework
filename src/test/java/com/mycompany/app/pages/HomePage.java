package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {

    private final boolean isMobile;
    
    public HomePage(Page page, boolean isMobile) {
        super(page); 
        this.isMobile = isMobile;
    }

    // --- DESKTOP APP LOCATORS ---
    private final String searchBar = "#searchBox";
    private final String viewCartButton = ".block__added-to-cart a[href*='Cart.aspx']";
    private final String checkoutBtn = ".block__added-to-cart a[href*='Checkout.aspx']";
    private final String addedToCartHeader = "#ctl00_mainContent_itemAddedToCart_txtTitle";
    private final String personalizationDetails = "#ctl00_mainContent_itemAddedToCart_txtAddToCartPers, #ctl00_belowHeader_itemUpdatedInfoControl_txtAddToCartPers";
    private final String forHerNavLink = ".nav-list > .nav-item > a:text-is('For Her')";

    // --- MOBILE APP LOCATORS ---
    private final String mobileSearchBar = "input.search-input.ui-autocomplete-input";
    private final String mobileForHerLink = "a[aria-label='for her']";
    
    // New mobile locators from the Item Added screen
    private final String mobileViewCartButton = ".divNextBtn input[value='View Cart']";
    private final String mobileCheckoutBtn = ".divNextBtn input[value='Checkout']";

    public void typeProduct(String productName) {
       String locator = isMobile ? mobileSearchBar : searchBar;
       page.locator(locator).click();
       page.locator(locator).fill(productName);
    }

    public void clickForHer() {
        String locator = isMobile ? mobileForHerLink : forHerNavLink;
        page.locator(locator).click();
    }

    public void searchProduct() {
        String locator = isMobile ? mobileSearchBar : searchBar;
        page.locator(locator).press("Enter");
    }

    public void clickViewCart() {
        String locator = isMobile ? mobileViewCartButton : viewCartButton;
        page.locator(locator).click(new Locator.ClickOptions().setForce(true));
    }

    public void clickCheckout() {
        String locator = isMobile ? mobileCheckoutBtn : checkoutBtn;
        page.locator(locator).click(new Locator.ClickOptions().setForce(true));
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