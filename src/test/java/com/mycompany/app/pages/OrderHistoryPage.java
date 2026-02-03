package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class OrderHistoryPage extends BasePage {

    private final String reorderBtn = ".btn_wrapper .reorderItem";
    private final String reportIssueBtn = ".btn_wrapper a:text-is('REPORT ISSUE')";
    private final String writeReviewBtn = ".btn_wrapper a:text-is('WRITE A REVIEW')";

    private final String productContainer = "td.cart-item-content";
    private final String itemNameLoc = productContainer + " > div:nth-child(1) > b:nth-child(1)";
    private final String itemNumberLoc = productContainer + " > div:nth-child(1) > b:nth-child(2)";
    private final String quantityLoc = productContainer + " >> text=Quantity: >> b";
    private final String priceLoc = productContainer + " .sale.info b";

    public OrderHistoryPage(Page page) {
        super(page);
    }

    private final String viewDetailsButton = ".content__wrapper .content-right-col a.base__btn";

    public void clickViewDetailsOfFirstOrder() {
        page.locator(viewDetailsButton).first().click();
    }

    public void clickReorder() {
        page.locator(reorderBtn).first().click();
    }

    public void clickReportIssue() {
        page.locator(reportIssueBtn).first().click();
    }

    public void clickWriteReview() {
        page.locator(writeReviewBtn).first().click();
    }

    public String getItemName() {
        return page.locator(itemNameLoc).first().innerText();
    }

    public String getItemNumber() {
        return page.locator(itemNumberLoc).first().innerText();
    }

    public String getItemQuantity() {
        return page.locator(quantityLoc).first().innerText();
    }

    public String getItemPrice() {
        return page.locator(priceLoc).first().innerText();
    }
}