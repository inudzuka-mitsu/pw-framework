package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class HomePage extends BasePage {

    private final String getStartedButton = "text=Get started";
    
    public HomePage(Page page) {
        super(page); 
    }

    public void navigate() {
        page.navigate("https://playwright.dev");
    }
}