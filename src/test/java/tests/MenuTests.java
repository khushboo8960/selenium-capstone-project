package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DriverFactory;
import org.openqa.selenium.By;

public class MenuTests {

    @BeforeMethod
    public void setUp() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2); // wait for page load
        login.login("standard_user", "secret_sauce");
        login.waitFor(3); // wait after login
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Test
    public void verifyUserCanLogout() {
        DriverFactory.getDriver().findElement(By.id("react-burger-menu-btn")).click();
        new LoginPage().waitFor(2); // wait for menu to open
        DriverFactory.getDriver().findElement(By.id("logout_sidebar_link")).click();
        new LoginPage().waitFor(3); // wait after logout

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("saucedemo.com"),
                "Logout did not return to login page");
    }

    @Test
    public void verifyAboutLink() {
        DriverFactory.getDriver().findElement(By.id("react-burger-menu-btn")).click();
        new LoginPage().waitFor(2); // wait for menu to open
        DriverFactory.getDriver().findElement(By.id("about_sidebar_link")).click();
        new LoginPage().waitFor(4); // wait for page to load

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("saucelabs.com"),
                "About link did not redirect to Sauce Labs site");
    }

    @Test
    public void verifyResetAppState() {
        // Add product to cart first
        DriverFactory.getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        new LoginPage().waitFor(2); // wait after adding product

        // Reset app state from menu
        DriverFactory.getDriver().findElement(By.id("react-burger-menu-btn")).click();
        new LoginPage().waitFor(2); // wait for menu
        DriverFactory.getDriver().findElement(By.id("reset_sidebar_link")).click();
        new LoginPage().waitFor(2); // wait after reset

        boolean badgePresent = DriverFactory.getDriver().findElements(By.className("shopping_cart_badge")).size() > 0;
        Assert.assertFalse(badgePresent, "Cart not cleared after Reset App State");
    }

    @Test
    public void verifyAllMenuOptionsVisible() {
        DriverFactory.getDriver().findElement(By.id("react-burger-menu-btn")).click();
        new LoginPage().waitFor(2); // wait for menu to open

        boolean allVisible =
                DriverFactory.getDriver().findElement(By.id("about_sidebar_link")).isDisplayed() &&
                DriverFactory.getDriver().findElement(By.id("logout_sidebar_link")).isDisplayed() &&
                DriverFactory.getDriver().findElement(By.id("reset_sidebar_link")).isDisplayed();

        new LoginPage().waitFor(2); // pause to show menu items
        Assert.assertTrue(allVisible, "Not all menu options are visible");
    }
}
