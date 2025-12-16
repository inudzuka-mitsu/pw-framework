package com.mycompany.app.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected static Properties props;

    @BeforeAll
    static void setupGlobal() throws IOException {
        String env = System.getProperty("env", "dev");

        props = new Properties();
        FileInputStream ip = new FileInputStream("src/test/resources/config-" + env.toLowerCase() + ".properties");
        props.load(ip);
        
        System.out.println(">>> Running tests on Environment: " + env.toUpperCase());
        
        playwright = Playwright.create();
    
        boolean isHeadless = Boolean.parseBoolean(getProperty("headless"));
        
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(isHeadless)
        );
    }

    @AfterAll
    static void tearDownGlobal() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void setupTest() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDownTest() {
        if (context != null) context.close();
    }


    public static String getProperty(String key) {
        String systemProp = System.getProperty(key);
        return (systemProp != null) ? systemProp : props.getProperty(key);
    }
}