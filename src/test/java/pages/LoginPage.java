package pages;

import org.openqa.selenium.By;
import utils.DriverFactory;

public class LoginPage {
    public void open() {
        DriverFactory.getDriver().get("https://www.saucedemo.com/");
    }

    public void login(String username, String password) {
        DriverFactory.getDriver().findElement(By.id("user-name")).sendKeys(username);
        DriverFactory.getDriver().findElement(By.id("password")).sendKeys(password);
        DriverFactory.getDriver().findElement(By.id("login-button")).click();
    }

    public String getErrorMessage() {
        return DriverFactory.getDriver().findElement(By.cssSelector("h3[data-test='error']")).getText();
    }

    public void waitFor(int seconds) {
        try { Thread.sleep(seconds * 1000); } catch (Exception e) {}
    }
}
