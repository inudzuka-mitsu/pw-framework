package com.mycompany.app.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected Properties props;

    @BeforeEach
    void setup() throws IOException {

        String env = System.getProperty("env", "dev");
        props = new Properties();
        FileInputStream ip = new FileInputStream("src/test/resources/config-" + env.toLowerCase() + ".properties");
        props.load(ip);
        
        System.out.println(">>> Starting test on: " + env.toUpperCase());

        playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(getProperty("headless"));
        
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(isHeadless)
        );

        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public String getProperty(String key) {
        String systemProp = System.getProperty(key);
        return (systemProp != null) ? systemProp : props.getProperty(key);
    }
}