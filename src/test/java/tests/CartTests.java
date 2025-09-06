package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.CartPage;
import pages.LoginPage;
import utils.DriverFactory;
import org.openqa.selenium.By;

public class CartTests {

    @BeforeMethod
    public void setUp() {
        LoginPage login = new LoginPage();
        login.open();
        login.waitFor(2); // wait for login page to load
        login.login("standard_user", "secret_sauce");
        login.waitFor(3); // wait after login
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Test
    public void verifyCartBadgeIncreases() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(3); // wait for cart badge to update

        String badge = DriverFactory.getDriver().findElement(By.className("shopping_cart_badge")).getText();
        inventory.waitFor(2); // pause for presentation visibility
        Assert.assertEquals(badge, "1", "Cart badge did not increase after adding product");
    }

    @Test
    public void verifyCartIsEmptyAfterRemovingItems() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);

        // Remove item
        DriverFactory.getDriver().findElement(
                By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button")
        ).click();
        inventory.waitFor(3); // wait for removal

        boolean badgePresent = DriverFactory.getDriver().findElements(By.className("shopping_cart_badge")).size() > 0;
        inventory.waitFor(2); // pause before assertion
        Assert.assertFalse(badgePresent, "Cart is not empty after removing all items");
    }

    @Test
    public void verifyNavigatingToCartPage() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);
        inventory.goToCart();
        inventory.waitFor(3); // wait for navigation

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("cart.html"),
                "User not navigated to cart page");
    }

    @Test
    public void verifyProductDetailsInCart() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);
        inventory.goToCart();
        inventory.waitFor(3); // wait for cart to load

        String productName = DriverFactory.getDriver().findElement(By.className("inventory_item_name")).getText();
        inventory.waitFor(2); // pause for visibility
        Assert.assertEquals(productName, "Sauce Labs Backpack", "Product name mismatch in cart");
    }

    @Test
    public void continueShoppingButtonWorks() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);
        inventory.goToCart();
        inventory.waitFor(2);

        DriverFactory.getDriver().findElement(By.id("continue-shopping")).click();
        new InventoryPage().waitFor(3); // wait for navigation

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"),
                "Continue shopping button did not navigate back to inventory page");
    }

    @Test
    public void checkoutButtonWorks() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);
        inventory.goToCart();
        inventory.waitFor(2);

        CartPage cart = new CartPage();
        cart.clickCheckout();
        cart.waitFor(3); // wait for navigation

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout-step-one.html"),
                "Checkout button did not navigate to checkout page");
    }
}
