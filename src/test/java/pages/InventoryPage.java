package pages;

import org.openqa.selenium.By;
import utils.DriverFactory;

public class InventoryPage {
    public void addBackpackToCart() {
        DriverFactory.getDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
    }

    public void goToCart() {
        DriverFactory.getDriver().findElement(By.className("shopping_cart_link")).click();
    }

    public void waitFor(int seconds) {
        try { Thread.sleep(seconds * 1000); } catch (Exception e) {}
    }
}
