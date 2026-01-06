package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class StagingLoginPage extends BasePage {

    public StagingLoginPage(Page page) {
        super(page);
    }

    String usernameInput = "input#action";
    String passwordInput = "input#magicword";
    String signInBtn = "input#pb-login-btn";

    public void login(String username, String password) {
        page.locator(usernameInput).fill(username);
        page.locator(passwordInput).fill(password);
        page.locator(signInBtn).click();
    }
}
    