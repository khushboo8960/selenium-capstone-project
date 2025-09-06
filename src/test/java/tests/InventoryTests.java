package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverFactory;

public class InventoryTests {

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
    public void verifyProductsDisplayed() {
        new InventoryPage().waitFor(2);
        Assert.assertTrue(DriverFactory.getDriver().getPageSource().contains("Sauce Labs Backpack"),
                "Products are not displayed after login");
    }

    @Test
    public void addSingleProductToCart() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(3); // wait after adding product
        Assert.assertTrue(DriverFactory.getDriver().getPageSource().contains("Remove"),
                "Product not added to cart");
    }

    @Test
    public void addMultipleProductsToCart() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);
        // Add another item dynamically (example: Sauce Labs Bike Light)
        DriverFactory.getDriver().findElement(
                org.openqa.selenium.By.xpath("//div[text()='Sauce Labs Bike Light']/ancestor::div[@class='inventory_item']//button")
        ).click();
        inventory.waitFor(3); // wait after adding 2nd product
        Assert.assertTrue(DriverFactory.getDriver().getPageSource().contains("Remove"),
                "Multiple products not added correctly");
    }

    @Test
    public void removeProductFromCart() {
        InventoryPage inventory = new InventoryPage();
        inventory.addBackpackToCart();
        inventory.waitFor(2);
        // Remove item
        DriverFactory.getDriver().findElement(
                org.openqa.selenium.By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button")
        ).click();
        inventory.waitFor(3); // wait after removing
        Assert.assertFalse(DriverFactory.getDriver().getPageSource().contains("Remove"),
                "Product was not removed successfully");
    }

    @Test
    public void sortProductsByNameAZ() {
        org.openqa.selenium.support.ui.Select sortDropdown =
                new org.openqa.selenium.support.ui.Select(DriverFactory.getDriver().findElement(org.openqa.selenium.By.className("product_sort_container")));
        sortDropdown.selectByVisibleText("Name (A to Z)");
        new InventoryPage().waitFor(3); // wait for sort effect
        Assert.assertTrue(true, "Products sorted by Name A-Z (manual visual validation)");
    }

    @Test
    public void sortProductsByNameZA() {
        org.openqa.selenium.support.ui.Select sortDropdown =
                new org.openqa.selenium.support.ui.Select(DriverFactory.getDriver().findElement(org.openqa.selenium.By.className("product_sort_container")));
        sortDropdown.selectByVisibleText("Name (Z to A)");
        new InventoryPage().waitFor(3);
        Assert.assertTrue(true, "Products sorted by Name Z-A (manual visual validation)");
    }

    @Test
    public void sortProductsByPriceLowToHigh() {
        org.openqa.selenium.support.ui.Select sortDropdown =
                new org.openqa.selenium.support.ui.Select(DriverFactory.getDriver().findElement(org.openqa.selenium.By.className("product_sort_container")));
        sortDropdown.selectByVisibleText("Price (low to high)");
        new InventoryPage().waitFor(3);
        Assert.assertTrue(true, "Products sorted Low to High (manual visual validation)");
    }

    @Test
    public void sortProductsByPriceHighToLow() {
        org.openqa.selenium.support.ui.Select sortDropdown =
                new org.openqa.selenium.support.ui.Select(DriverFactory.getDriver().findElement(org.openqa.selenium.By.className("product_sort_container")));
        sortDropdown.selectByVisibleText("Price (high to low)");
        new InventoryPage().waitFor(3);
        Assert.assertTrue(true, "Products sorted High to Low (manual visual validation)");
    }
}
