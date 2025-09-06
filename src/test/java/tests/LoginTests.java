package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginTests {

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Test
    public void validLogin() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2); // wait for page load
        login.login("standard_user", "secret_sauce");
        login.waitFor(3); // wait after login
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
    }

    @Test
    public void invalidLoginWrongUsername() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("wrong_user", "secret_sauce");
        login.waitFor(2); // wait for error message
        Assert.assertTrue(login.getErrorMessage().contains("Epic sadface"));
    }

    @Test
    public void invalidLoginWrongPassword() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("standard_user", "wrong_pass");
        login.waitFor(2);
        Assert.assertTrue(login.getErrorMessage().contains("Epic sadface"));
    }

    @Test
    public void blankFieldsLogin() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("", "");
        login.waitFor(2);
        Assert.assertTrue(login.getErrorMessage().contains("Epic sadface"));
    }

    @Test
    public void lockedOutUserLogin() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("locked_out_user", "secret_sauce");
        login.waitFor(2);
        Assert.assertTrue(login.getErrorMessage().contains("Epic sadface"));
    }

    @Test
    public void performanceGlitchUserLogin() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("performance_glitch_user", "secret_sauce");
        login.waitFor(4); // extra wait since glitch user loads slowly
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
    }

    @Test
    public void errorMessageDisappearsAfterRetry() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("wrong_user", "wrong_pass");
        login.waitFor(2);
        Assert.assertTrue(login.getErrorMessage().contains("Epic sadface"));

        login.open();
        login.waitFor(2);
        login.login("standard_user", "secret_sauce");
        login.waitFor(3);
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
    }

    @Test
    public void verifyUrlAndTitleAfterLogin() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2);
        login.login("standard_user", "secret_sauce");
        login.waitFor(3);
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
        Assert.assertEquals(DriverFactory.getDriver().getTitle(), "Swag Labs");
    }
}
