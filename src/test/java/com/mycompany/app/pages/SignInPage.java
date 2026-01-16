package com.mycompany.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SignInPage extends BasePage {

    public SignInPage(Page page) {
        super(page);
    }

    private final String emailInput = "input[id*='Email']";
    private final String passwordInput = "input[id*='Password']";

    public void enterEmail(String email) {
        page.locator(emailInput).fill(email);
    }

    public void enterPassword(String password) {
        page.locator(passwordInput).fill(password);
    }

    public void clickSignIn() {
        Locator submitButton = page.locator(
            "button:has-text('Sign In'), " +       
            "input[value='Sign In'], " +          
            "input[id*='SignIn'], " +             
            "input[id*='Login'], " +               
            "[type='submit']"                     
        ).first();
        submitButton.click(new Locator.ClickOptions().setForce(true));
    }

    public void signIn(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }
}
