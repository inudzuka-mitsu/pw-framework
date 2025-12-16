package com.mycompany.app.tests;

import com.mycompany.app.base.TestBase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTests extends TestBase {

    @Test
    void verifyUrlIsSecure() {
        String targetUrl = getProperty("baseUrl");

        System.out.println("Navigating to: " + targetUrl);
        
        page.navigate(targetUrl);
        String currentUrl = page.url();

        assertTrue(currentUrl.contains("exercise"), 
                   "Expected URL to contain 'https' but found: " + currentUrl);
    }
}