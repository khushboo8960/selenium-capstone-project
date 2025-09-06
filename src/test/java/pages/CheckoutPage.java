package pages;

import org.openqa.selenium.By;
import utils.DriverFactory;

public class CheckoutPage {
    public void enterDetails(String firstName, String lastName, String zip) {
        DriverFactory.getDriver().findElement(By.id("first-name")).sendKeys(firstName);
        DriverFactory.getDriver().findElement(By.id("last-name")).sendKeys(lastName);
        DriverFactory.getDriver().findElement(By.id("postal-code")).sendKeys(zip);
        DriverFactory.getDriver().findElement(By.id("continue")).click();
    }

    public void finishOrder() {
        DriverFactory.getDriver().findElement(By.id("finish")).click();
    }

    public String getSuccessMessage() {
        return DriverFactory.getDriver().findElement(By.className("complete-header")).getText();
    }

    public void waitFor(int seconds) {
        try { Thread.sleep(seconds * 1000); } catch (Exception e) {}
    }
}
