package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomeTest extends BaseTest {

    // ADDED: Test to verify all important elements are visible on homepage
    @Test(priority = 1, description = "Verify homepage main elements are visible")
    public void verifyHomePageElementsVisible() {
        HomePage homePage = new HomePage(DriverFactory.getDriver()); // FIXED: Pass driver to constructor

        // ADDED: Verify important elements are displayed before any clicks
        Assert.assertTrue(homePage.isLogoVisible(), "Logo should be visible");
        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be visible");
        Assert.assertTrue(homePage.isRegisterLinkDisplayed(), "Register link should be visible");
        Assert.assertTrue(homePage.isSearchBoxVisible(), "Search box should be visible");
        Assert.assertTrue(homePage.isCartVisible(), "Cart should be visible");
        Assert.assertTrue(homePage.isWishlistVisible(), "Wishlist should be visible");
    }

    // IMPROVED: Separate test for each navigation to make debugging easier
    @Test(priority = 2, description = "Verify clicking login link navigates to login page")
    public void navigateToLoginPageTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Click login link
        homePage.clickLogin();

        // ADDED: Verify we're on login page
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"), "Should navigate to login page");
    }

//    @Test(priority = 3, description = "Verify clicking register link navigates to register page")
//    public void navigateToRegisterPageTest() {
//        HomePage homePage = new HomePage(DriverFactory.getDriver());
//
//        // Click register link
//        homePage.clickRegister();
//
//        // ADDED: Verify we're on register page
//        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("/register"), "Should navigate to register page");
//    }

    @Test(priority = 4, description = "Verify clicking cart navigates to shopping cart page")
    public void navigateToCartTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Click cart
        homePage.clickCart();

        // ADDED: Verify we're on cart page
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/cart"), "Should navigate to cart page");
    }

    @Test(priority = 5, description = "Verify clicking wishlist navigates to wishlist page")
    public void navigateToWishlistTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Click wishlist
        homePage.clickWishlist(); // FIXED: Changed from clickWhichList() to clickWishlist()

        // ADDED: Verify we're on wishlist page
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/wishlist"), "Should navigate to wishlist page");
    }

    @Test(priority = 6, description = "Verify search functionality works")
    public void searchFunctionalityTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // IMPROVED: Use the combined search method instead of two separate calls
        String searchTerm = "laptop"; // CHANGED: Use real product name
        homePage.search(searchTerm); // This does both: enters text AND clicks search button

        // ADDED: Verify search was performed
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search"), "URL should contain 'search' after searching");
        // OPTIONAL: Could also verify search term appears in URL
        // Assert.assertTrue(currentUrl.contains(searchTerm), "Search term should be in URL");
    }

    @Test(priority = 7, description = "Verify logo click returns to homepage")
    public void logoNavigationTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Navigate away from home page first
        homePage.clickLogin();

        // ADDED: Verify we left home page
        String urlAfterLogin = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(urlAfterLogin.contains("/login"), "Should be on login page");


        homePage.clickLogo(); // FIXED: Logo click should be on LoginPage, not HomePage

        // ADDED: Verify we're back on homepage
        String urlAfterLogo = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(
                urlAfterLogo.equals("https://demowebshop.tricentis.com/") ||
                        !urlAfterLogo.contains("/login"),
                "Should return to homepage after clicking logo"
        );
    }

    // ADDED: Test for navigating to Books category
    @Test(priority = 8, description = "Verify navigation to Books category page works")
    public void navigateToBooksCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Click Books category
        homePage.clickBooksCategory();

        // Verify navigation to Books page
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("/books"), "URL should contain '/books' after navigating to Books category");
    }

    // ADDED: Test for navigating to Computers category
    @Test(priority = 9, description = "Verify navigation to Computers category page works")
    public void navigateToComputersCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Click Computers category
        homePage.clickComputersCategory();

        // Verify navigation to Computers page
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("/computers"), "URL should contain '/computers' after navigating to Computers category");
    }

    // ADDED: Test for navigating to Electronics category
    @Test(priority = 10, description = "Verify navigation to Electronics category page works")
    public void navigateToElectronicsCategoryTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());

        // Click Electronics category
        homePage.clickElectronicsCategory();

        // Verify navigation to Electronics page
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("/electronics"), "URL should contain '/electronics' after navigating to Electronics category");
    }

    // ADDED: Test logout functionality (requires login first)
//    @Test(priority = 11, description = "Verify logout functionality works")
//    public void logoutFunctionalityTest() {
//        // Step 1: Login first (logout requires being logged in)
//        HomePage homePage = new HomePage(DriverFactory.getDriver());
//        homePage.clickLogin();
//
//        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
//        loginPage.login("testuser@example.com", "Password123"); // CHANGE: Use valid credentials
//
//        // Step 2: Verify user is logged in
//        homePage = new HomePage(DriverFactory.getDriver()); // Create new HomePage instance
//        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in before logout test");
//
//        // Step 3: Click logout
//        homePage.clickLogout();
//
//        // Step 4: Verify user is logged out
//        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be visible after logout");
//        Assert.assertFalse(homePage.isUserLoggedIn(), "User should not be logged in after logout");
//    }

    // REMOVED: Old test that clicked everything without assertions
    // The old homePageElementsTest() was just clicking without verifying anything
    // Split into separate focused tests above
}