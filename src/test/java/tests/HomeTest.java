package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

@Epic("E-Commerce Platform")
@Feature("Homepage Functionality")
public class HomeTest extends BaseTest {

    @Test(priority = 1, description = "Verify homepage main elements are visible",groups = {"smoke"})
    @Story("Homepage UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate that all essential homepage elements (logo, navigation links, search, cart, wishlist) are visible to users")
    public void verifyHomePageElementsVisible() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        Assert.assertTrue(homePage.isLogoVisible(), "Logo should be visible on homepage");
        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be visible");
        Assert.assertTrue(homePage.isRegisterLinkDisplayed(), "Register link should be visible");
        Assert.assertTrue(homePage.isSearchBoxVisible(), "Search box should be visible");
        Assert.assertTrue(homePage.isCartVisible(), "Cart icon should be visible");
        Assert.assertTrue(homePage.isWishlistVisible(), "Wishlist icon should be visible");
    }

    @Test(priority = 2, description = "Verify clicking login link navigates to login page",groups = {"smoke"})
    @Story("User Authentication Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking the login link successfully navigates user to the login page")
    public void navigateToLoginPageTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickLogin();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "The URL should not be null");
        Assert.assertTrue(currentUrl.contains("/login"),
                "Expected URL to contain '/login', but got: " + currentUrl);
    }

    @Test(priority = 3, description = "Verify clicking register link navigates to register page",groups = {"smoke"})
    @Story("User Registration Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking the register link successfully navigates user to the registration page")
    public void navigateToRegisterPageTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickRegister();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "The URL should not be null");
        Assert.assertTrue(currentUrl.contains("/register"),
                "Expected URL to contain '/register', but got: " + currentUrl);
    }

    @Test(priority = 4, description = "Verify clicking cart navigates to shopping cart page",groups = {"smoke"})
    @Story("Shopping Cart Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the cart icon navigates user to the shopping cart page")
    public void navigateToCartTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickCart();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "The URL should not be null");
        Assert.assertTrue(currentUrl.contains("/cart"),
                "Expected URL to contain '/cart', but got: " + currentUrl);
    }

    @Test(priority = 5, description = "Verify clicking wishlist navigates to wishlist page",groups = {"smoke"})
    @Story("Wishlist Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the wishlist icon navigates user to the wishlist page")
    public void navigateToWishlistTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickWishlist();

        String currentUrl = homePage.getUrl();
        Assert.assertNotNull(currentUrl, "The URL should not be null");
        Assert.assertTrue(currentUrl.contains("/wishlist"),
                "Expected URL to contain '/wishlist', but got: " + currentUrl);
    }

//    @Test(priority = 6, description = "Verify search functionality works")
//    @Story("Product Search")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Verify that users can search for products and are redirected to search results page with correct search term")
//    public void searchFunctionalityTest() {
//        HomePage homePage = new HomePage(DriverFactory.getDriver());
//
//        String searchTerm = "laptop";
//        homePage.search(searchTerm);
//
//        String currentUrl = homePage.getUrl();
//        Assert.assertNotNull(currentUrl, "The URL should not be null");
//        Assert.assertTrue(currentUrl.contains("search"),
//                "Expected URL to contain 'search', but got: " + currentUrl);
//        Assert.assertTrue(currentUrl.contains(searchTerm),
//                "Expected search term '" + searchTerm + "' in URL, but got: " + currentUrl);
//    }

    @Test(priority = 7, description = "Verify logo click returns to homepage",groups = {"smoke"})
    @Story("Homepage Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the logo from any page returns user to the homepage")
    public void logoNavigationTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickLogin();

        String urlAfterLogin = homePage.getUrl();
        Assert.assertNotNull(urlAfterLogin, "The URL should not be null");
        Assert.assertTrue(urlAfterLogin.contains("/login"), "Should be on login page");

        homePage.clickLogo();

        String urlAfterLogo = homePage.getUrl();
        Assert.assertTrue(
                urlAfterLogo.equals("https://demowebshop.tricentis.com/") ||
                        !urlAfterLogo.contains("/login"),
                "Should return to homepage after clicking logo, but got: " + urlAfterLogo
        );
    }

    @Test(priority = 8, description = "Verify navigation to Books category page works",groups = {"smoke"})
    @Story("Category Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation to Books category page displays correct URL")
    public void navigateToBooksCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickBooksCategory();

        String url = homePage.getUrl();
        Assert.assertTrue(url.contains("/books"),
                "Expected URL to contain '/books', but got: " + url);
    }

    @Test(priority = 9, description = "Verify navigation to Computers category page works",groups = {"smoke"})
    @Story("Category Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation to Computers category page displays correct URL")
    public void navigateToComputersCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickComputersCategory();

        String url = homePage.getUrl();
        Assert.assertTrue(url.contains("/computers"),
                "Expected URL to contain '/computers', but got: " + url);
    }

    @Test(priority = 10, description = "Verify navigation to Electronics category page works",groups = {"smoke"})
    @Story("Category Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation to Electronics category page displays correct URL")
    public void navigateToElectronicsCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.clickElectronicsCategory();

        String url = homePage.getUrl();
        Assert.assertTrue(url.contains("/electronics"),
                "Expected URL to contain '/electronics', but got: " + url);
    }

    @Test(priority = 11, description = "Verify logout functionality works",groups = {"smoke"})
    @Story("User Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that logged-in users can successfully logout and session is terminated properly")
    public void logoutFunctionalityTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.get("validEmail"), ConfigReader.get("validPassword"));

        homePage = new HomePage(DriverFactory.getDriver());
        Assert.assertTrue(homePage.isUserLoggedIn(),
                "User should be logged in before logout test");

        homePage.clickLogout();

        Assert.assertTrue(homePage.isLoginLinkDisplayed(),
                "Login link should be visible after logout");
        Assert.assertFalse(homePage.isUserLoggedIn(),
                "User should not be logged in after logout");
    }
}