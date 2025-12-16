package com.mycompany.app.pages;

import com.microsoft.playwright.Page;

public class BasePage {
    
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void click(String selector) {
        page.click(selector);
    }

    public void type(String selector, String text) {
        page.fill(selector, text);
    }
    
    public String getTitle() {
        return page.title();
    }
}