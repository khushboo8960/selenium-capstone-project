package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.*;
import utils.DriverFactory;

import java.time.Duration;

public class CheckoutSteps {

    LoginPage login = new LoginPage();
    InventoryPage inventory = new InventoryPage();
    CartPage cart = new CartPage();
    CheckoutPage checkout = new CheckoutPage();

    WebDriver driver = DriverFactory.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as_with_password(String username, String password) {
        login.open();
        login.login(username, password);
        login.waitFor(2); // for demo visibility
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    @Given("I have added {string} to the cart")
    public void i_have_added_to_the_cart(String productName) {
        By productBtn = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button");
        wait.until(ExpectedConditions.elementToBeClickable(productBtn)).click();
        inventory.goToCart();
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    @When("I proceed to checkout with firstname {string}, lastname {string} and postalcode {string}")
    public void i_proceed_to_checkout(String firstname, String lastname, String postalcode) {
        cart.clickCheckout();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        checkout.enterDetails(firstname, lastname, postalcode);
        checkout.waitFor(2);
    }

    @Then("I should reach the checkout overview page")
    public void i_should_reach_the_checkout_overview_page() {
        String pageTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("title"))
        ).getText();

        Assert.assertTrue(pageTitle.equalsIgnoreCase("Checkout: Overview"),
                "Expected Checkout: Overview but got " + pageTitle);
    }

    @Then("I should see the message {string}")
    public void i_should_see_the_message(String expectedMsg) {
        String bodyText = driver.getPageSource();
        Assert.assertTrue(bodyText.contains(expectedMsg),
                "Expected message: " + expectedMsg + " but not found!");
    }

    @When("I click on finish")
    public void i_click_on_finish() {
        checkout.finishOrder();
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
    }
}

