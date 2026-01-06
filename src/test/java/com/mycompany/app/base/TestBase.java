package com.mycompany.app.base;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.Allure;

public class TestBase {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected Properties props;

    @RegisterExtension
    public final TestWatcher watcher = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println(">>> ❌ Test Failed. Capturing screenshot...");
            try {
                if (page != null) {
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                    Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
                }
            } catch (Exception e) {
                System.out.println(">>> Failed to take screenshot: " + e.getMessage());
            } finally {
                closeBrowser();
            }
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            closeBrowser();
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            closeBrowser();
        }
    };

    @BeforeEach
    void setup() throws IOException {
        String env = System.getProperty("env", "dev");
        props = new Properties();
        if (env == null) env = "dev";
        
        FileInputStream ip = new FileInputStream("src/test/resources/config-" + env.toLowerCase() + ".properties");
        props.load(ip);

        System.out.println(">>> Starting test on: " + env.toUpperCase());

        playwright = Playwright.create();
        
        String headlessVal = getProperty("headless");
        boolean isHeadless = (headlessVal != null) && Boolean.parseBoolean(headlessVal);

        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(isHeadless)
        );

        context = browser.newContext();
        page = context.newPage();
    }

    private void closeBrowser() {
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    public String getProperty(String key) {
        String systemProp = System.getProperty(key);
        return (systemProp != null) ? systemProp : props.getProperty(key);
    }
}