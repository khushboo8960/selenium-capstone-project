package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginSteps {

    LoginPage login = new LoginPage();

    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_login_page() {
        login.open();
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        login.login(username, password);
        login.waitFor(2);
    }

    @Then("I should be navigated to the inventory page")
    public void i_should_be_navigated_to_the_inventory_page() {
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"));
    }

    @Then("I should see an error message containing {string}")
    public void i_should_see_an_error_message_containing(String expectedMsg) {
        Assert.assertTrue(login.getErrorMessage().contains(expectedMsg));
    }
}
