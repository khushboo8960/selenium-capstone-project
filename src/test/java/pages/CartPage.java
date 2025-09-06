package pages;

import org.openqa.selenium.By;
import utils.DriverFactory;

public class CartPage {
    public void clickCheckout() {
        DriverFactory.getDriver().findElement(By.id("checkout")).click();
    }

    public void waitFor(int seconds) {
        try { Thread.sleep(seconds * 1000); } catch (Exception e) {}
    }
}
