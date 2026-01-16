package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage extends BasePage {

    public CheckoutPage(Page page) {
        super(page);
    }

    private final String couponInput = "#txtCouponCode";
    private final String applyCouponBtn = "#ctl00_belowHeader_cmdReviseDiscout";

    private final String creditCardRadio = "#ctl00_belowHeader_payCC";
    private final String cardTypeDropdown = "#ctl00_belowHeader_cardType";
    private final String nameOnCardInput = "#ctl00_belowHeader_nameOnCard";
    private final String cardNumberInput = "#ctl00_belowHeader_cardNumber";
    private final String cvvInput = "#ctl00_belowHeader_validationNumber";
    private final String expMonthDropdown = "#ctl00_belowHeader_expMonth";
    private final String expYearDropdown = "#ctl00_belowHeader_expYear";

    private final String placeOrderBtn = "#cmdPlaceOrder";

    public void applyCoupon(String code) {
        page.locator(couponInput).fill(code);
        page.locator(applyCouponBtn).click();
    }

    public void enterPaymentInformation(String cardType, String name, String number, String cvv, String month, String year) {
        page.locator(creditCardRadio).check(new Locator.CheckOptions().setForce(true));

        page.locator(cardTypeDropdown).selectOption(cardType);

        page.locator(nameOnCardInput).fill(name);
        page.locator(cardNumberInput).fill(number);
        page.locator(cvvInput).fill(cvv);

        page.locator(expMonthDropdown).selectOption(month);
        page.locator(expYearDropdown).selectOption(year);
    }

    public void placeOrder() {
        page.locator(placeOrderBtn).click(new Locator.ClickOptions().setForce(true));
    }
}
