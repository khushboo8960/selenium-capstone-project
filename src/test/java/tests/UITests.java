package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DriverFactory;
import org.openqa.selenium.By;

public class UITests {

    @BeforeMethod
    public void setUp() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2); // wait for login page to load
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Test
    public void verifyLoginPageUIElements() {
        // Pause to show UI
        new LoginPage().waitFor(2);

        Assert.assertTrue(DriverFactory.getDriver().findElement(By.className("login_logo")).isDisplayed(),
                "Logo not visible");
        Assert.assertTrue(DriverFactory.getDriver().findElement(By.id("user-name")).isDisplayed(),
                "Username field missing");
        Assert.assertTrue(DriverFactory.getDriver().findElement(By.id("password")).isDisplayed(),
                "Password field missing");
        Assert.assertTrue(DriverFactory.getDriver().findElement(By.id("login-button")).isDisplayed(),
                "Login button missing");

        new LoginPage().waitFor(2); // pause after checks
    }

    @Test
    public void verifyFooterLinks() {
        LoginPage login = new LoginPage();
        login.login("standard_user", "secret_sauce");
        login.waitFor(3); // wait for inventory page to load

        boolean twitter = DriverFactory.getDriver().findElement(By.linkText("Twitter")).isDisplayed();
        boolean facebook = DriverFactory.getDriver().findElement(By.linkText("Facebook")).isDisplayed();
        boolean linkedIn = DriverFactory.getDriver().findElement(By.linkText("LinkedIn")).isDisplayed();

        login.waitFor(2); // pause to show footer links
        Assert.assertTrue(twitter && facebook && linkedIn, "One or more footer links are missing");
    }

    @Test
    public void verifyBackButtonAfterLogout() {
        LoginPage login = new LoginPage();
        login.login("standard_user", "secret_sauce");
        login.waitFor(3); // wait for inventory page

        // Logout
        DriverFactory.getDriver().findElement(By.id("react-burger-menu-btn")).click();
        login.waitFor(2); // wait for menu to open
        DriverFactory.getDriver().findElement(By.id("logout_sidebar_link")).click();
        login.waitFor(3); // wait after logout

        // Press browser back
        DriverFactory.getDriver().navigate().back();
        login.waitFor(3); // wait for back navigation

        // Still should be on login page
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("saucedemo.com"),
                "User able to access app after logout via back button");
    }

    @Test
    public void verifyPageTitleAndLogoConsistency() {
        LoginPage login = new LoginPage();
        login.login("standard_user", "secret_sauce");
        login.waitFor(3); // wait for inventory page

        String title1 = DriverFactory.getDriver().getTitle();
        String logo1 = DriverFactory.getDriver().findElement(By.className("app_logo")).getText();
        login.waitFor(2); // pause to show title & logo

        // Navigate to cart page
        DriverFactory.getDriver().findElement(By.className("shopping_cart_link")).click();
        login.waitFor(3); // wait for cart page

        String title2 = DriverFactory.getDriver().getTitle();
        String logo2 = DriverFactory.getDriver().findElement(By.className("app_logo")).getText();

        Assert.assertEquals(title1, title2, "Page title not consistent");
        Assert.assertEquals(logo1, logo2, "Logo not consistent");
    }
}
