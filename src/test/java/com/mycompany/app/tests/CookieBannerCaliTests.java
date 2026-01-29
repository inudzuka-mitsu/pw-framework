package com.mycompany.app.tests;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.assertions.LocatorAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.login.StagingLoginPage;

public class CookieBannerCaliTests extends TestBase {

    @Test
    public void testCaliforniaCookieBanner() {
        BrowserContext caContext = browser.newContext();
        Page caPage = caContext.newPage();

        caPage.route("**/notice?**", route -> {
            System.out.println("⚡️ Intercepted TrustArc Notice Request!");
            
            APIResponse response = route.fetch();
            String originalBody = response.text();
            
            String hackedBody = originalBody.replace("state:\"\"", "state:\"CA\"");
            hackedBody = hackedBody.replace("behavior:\"implied\"", "behavior:\"expressed\"");
            
            hackedBody = hackedBody
                .replace("enableCCPA:!1", "enableCCPA:!0")
                .replace("ccpaApplies:!1", "ccpaApplies:!0")
                .replace("enableBanner:!1", "enableBanner:!0");

            if (hackedBody.contains("state:\"CA\"")) {
                System.out.println("✅ Successfully injected California State.");
            } else {
                System.out.println("❌ Failed to inject State (Mismatch).");
            }

            route.fulfill(new Route.FulfillOptions()
                .setBody(hackedBody)
                .setStatus(200)
                .setHeaders(response.headers())
                .setContentType("application/json")
            );
        });

        caPage.navigate(getProperty("stagingBaseUrl"));

        StagingLoginPage caLoginPage = new StagingLoginPage(caPage);
        caLoginPage.closePopUp();

        Locator cookieHeader = caPage.frameLocator("iframe[name='trustarc_cm']")
                                 .locator(".mainHeader.consentHeader h1");
    
        assertThat(cookieHeader).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
        assertThat(cookieHeader).containsText("Cookies and Related Technologies on This Site");

        System.out.println("✅ Cookie Banner (inside iframe) validated!");
        
        caContext.close();
    }
}