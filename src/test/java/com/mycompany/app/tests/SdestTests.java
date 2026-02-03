package com.mycompany.app.tests;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.mycompany.app.base.TestBase;
import com.mycompany.app.pages.ForHerPage;
import com.mycompany.app.pages.HomePage;
import com.mycompany.app.pages.ProductCatalogPage;
import com.mycompany.app.pages.login.StagingLoginPage;

public class SdestTests extends TestBase {

    private StagingLoginPage stagingLoginPage;
    private HomePage homePage;
    private ForHerPage forHerPage;
    private ProductCatalogPage pcp;

    @BeforeEach
    @SuppressWarnings("unused")
    void setupPages() {
        stagingLoginPage = new StagingLoginPage(page);
        homePage = new HomePage(page);
        forHerPage = new ForHerPage(page);
        pcp = new ProductCatalogPage(page);

        page.navigate(getProperty("stagingBaseUrl"));
        stagingLoginPage.closePopUp();
    }

    @Test
    @DisplayName("Validate URL parameters when navigating via 'For Her' category")
    void verifyForHerProductUrlParameters() {
        homePage.clickForHer();
        forHerPage.clickTopPicks();
        forHerPage.clickFirstProduct();

        assertThat(page).hasURL(Pattern.compile(".*sdest=.*&sdestid=.*&storeid=.*&categoryid=.*"));
    }

    @Test
    @DisplayName("Validate URL parameters when navigating via Search Results")
    void verifySearchResultsUrlParameters() {
        String searchQuery = "ornaments";

        homePage.typeProduct(searchQuery);
        homePage.searchProduct();
        pcp.clickFirstProduct();

        assertThat(page).hasURL(Pattern.compile(".*productid=.*&sdest=.*&sdestid=.*"));
    }
}
