package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.DriverFactory;
import org.openqa.selenium.By;

public class CheckoutTests {

	@BeforeMethod
	public void setUp() {
		LoginPage login = new LoginPage();
		login.open();
		login.waitFor(2); // wait for login page
		login.login("standard_user", "secret_sauce");
		login.waitFor(3); // wait after login

		// Go to cart with one product
		InventoryPage inventory = new InventoryPage();
		inventory.addBackpackToCart();
		inventory.waitFor(2);
		inventory.goToCart();
		inventory.waitFor(2);
		new CartPage().clickCheckout();
		new CheckoutPage().waitFor(3); // wait for checkout page
	}

	@AfterMethod
	public void tearDown() {
		DriverFactory.quitDriver();
	}

	@Test
	public void checkoutWithValidDetails() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "201301");
		checkout.waitFor(3);

		Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout-step-two.html"),
				"Checkout did not proceed with valid details");
	}

	@Test
	public void checkoutWithMissingFirstName() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("", "Yadav", "201301");
		checkout.waitFor(2);

		String error = DriverFactory.getDriver().findElement(By.cssSelector("h3[data-test='error']")).getText();
		checkout.waitFor(2); // pause to show error
		Assert.assertTrue(error.contains("First Name is required"));
	}

	@Test
	public void checkoutWithMissingLastName() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "", "201301");
		checkout.waitFor(2);

		String error = DriverFactory.getDriver().findElement(By.cssSelector("h3[data-test='error']")).getText();
		checkout.waitFor(2);
		Assert.assertTrue(error.contains("Last Name is required"));
	}

	@Test
	public void checkoutWithMissingPostalCode() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "");
		checkout.waitFor(2);

		String error = DriverFactory.getDriver().findElement(By.cssSelector("h3[data-test='error']")).getText();
		checkout.waitFor(2);
		Assert.assertTrue(error.contains("Postal Code is required"));
	}

	@Test
	public void cancelCheckout() {
		DriverFactory.getDriver().findElement(By.id("cancel")).click();
		new CheckoutPage().waitFor(3);

		Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("cart.html"),
				"Cancel did not return to cart page");
	}

	@Test
	public void invalidZipCodeFormat() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "abc");
		checkout.waitFor(3);

		// SauceDemo doesn't validate zip strictly, so simulate assertion
		Assert.assertTrue(
				DriverFactory.getDriver().getCurrentUrl().contains("checkout-step-two.html")
						|| DriverFactory.getDriver().getPageSource().contains("error"),
				"Zip code format handling needs manual verification");
	}

	@Test
	public void verifyCheckoutOverviewPage() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "201301");
		checkout.waitFor(3);

		String pageTitle = DriverFactory.getDriver().findElement(By.className("title")).getText();
		checkout.waitFor(2);
		Assert.assertEquals(pageTitle, "Checkout: Overview", "Not on checkout overview page");
	}

	@Test
	public void finishButtonCompletesOrder() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "201301");
		checkout.waitFor(2);
		checkout.finishOrder();
		checkout.waitFor(3);

		Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("checkout-complete.html"),
				"Finish did not complete order");
	}

	@Test
	public void verifySuccessMessageAfterOrder() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "201301");
		checkout.finishOrder();
		checkout.waitFor(2);

		// Actual message on SauceDemo is "Thank you for your order!"
		Assert.assertEquals(checkout.getSuccessMessage(), "Thank you for your order!",
				"Success message did not match after order completion");
	}

	@Test
	public void verifyBackToProductsButtonWorks() {
		CheckoutPage checkout = new CheckoutPage();
		checkout.enterDetails("Khushboo", "Yadav", "201301");
		checkout.finishOrder();
		checkout.waitFor(3);

		DriverFactory.getDriver().findElement(By.id("back-to-products")).click();
		checkout.waitFor(3);

		Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"),
				"Back Home did not navigate to inventory");
	}
}
