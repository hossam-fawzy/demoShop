package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;


public class HomeTest extends BaseTest {


    @Test(priority = 1, description = "Verify homepage main elements are visible")
    public void verifyHomePageElementsVisible() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        Assert.assertTrue(homePage.isLogoVisible(), "Logo should be visible");
        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be visible");
        Assert.assertTrue(homePage.isRegisterLinkDisplayed(), "Register link should be visible");
        Assert.assertTrue(homePage.isSearchBoxVisible(), "Search box should be visible");
        Assert.assertTrue(homePage.isCartVisible(), "Cart should be visible");
        Assert.assertTrue(homePage.isWishlistVisible(), "Wishlist should be visible");
    }


    @Test(priority = 2, description = "Verify clicking login link navigates to login page")
    public void navigateToLoginPageTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickLogin();
        // ADDED: Verify we're on login page
        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "the url should not be null");
        Assert.assertTrue(currentUrl.contains("/login"), "Should navigate to login page");
    }

    @Test(priority = 3, description = "Verify clicking register link navigates to register page")
    public void navigateToRegisterPageTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickRegister();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "the url should not be null");
        Assert.assertTrue(currentUrl.contains("/register"), "Should navigate to register page");
    }

    @Test(priority = 4, description = "Verify clicking cart navigates to shopping cart page")
    public void navigateToCartTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickCart();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "the url should not be null");
        Assert.assertTrue(currentUrl.contains("/cart"), "Should navigate to cart page");
    }

    @Test(priority = 5, description = "Verify clicking wishlist navigates to wishlist page")
    public void navigateToWishlistTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickWishlist();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "the url should not be null");
        Assert.assertTrue(currentUrl.contains("/wishlist"), "Should navigate to wishlist page");
    }

    @Test(priority = 6, description = "Verify search functionality works")
    public void searchFunctionalityTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        String searchTerm = "laptop"; // CHANGED: Use real product name
        homePage.search(searchTerm); // This does both: enters text AND clicks search button


        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "the url should not be null");
        Assert.assertTrue(currentUrl.contains("search"), "URL should contain 'search' after searching");

         Assert.assertTrue(currentUrl.contains(searchTerm), "Search term should be in URL");
    }

    @Test(priority = 7, description = "Verify logo click returns to homepage")
    public void logoNavigationTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickLogin();

        String urlAfterLogin = homePage.getUrl();
        Assert.assertNotNull(urlAfterLogin, "the url should not be null");
        Assert.assertTrue(urlAfterLogin.contains("/login"), "Should be on login page");

        homePage.clickLogo();

        String urlAfterLogo = homePage.getUrl();
        Assert.assertTrue(
                urlAfterLogo.equals("https://demowebshop.tricentis.com/") ||
                        !urlAfterLogo.contains("/login"),
                "Should return to homepage after clicking logo"
        );
    }


    @Test(priority = 8, description = "Verify navigation to Books category page works")
    public void navigateToBooksCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickBooksCategory();

        String url = homePage.getUrl();
        Assert.assertTrue(url.contains("/books"), "URL should contain '/books' after navigating to Books category");
    }

    // ADDED: Test for navigating to Computers category
    @Test(priority = 9, description = "Verify navigation to Computers category page works")
    public void navigateToComputersCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickComputersCategory();

        String url = homePage.getUrl();
        Assert.assertTrue(url.contains("/computers"), "URL should contain '/computers' after navigating to Computers category");
    }


    @Test(priority = 10, description = "Verify navigation to Electronics category page works")
    public void navigateToElectronicsCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickElectronicsCategory();

        String url = homePage.getUrl();
        Assert.assertTrue(url.contains("/electronics"), "URL should contain '/electronics' after navigating to Electronics category");
    }


    @Test(priority = 11, description = "Verify logout functionality works")
    public void logoutFunctionalityTest() {

        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.get("validEmail") , ConfigReader.get("validPassword"));

        homePage = new HomePage(DriverFactory.getDriver()); // Create new HomePage instance
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in before logout test");

        homePage.clickLogout();

        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be visible after logout");
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should not be logged in after logout");
    }


}