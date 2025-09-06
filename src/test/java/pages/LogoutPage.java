package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends BasePage {
	@FindBy(id = "react-burger-menu-btn")
	private WebElement menuButton;
	@FindBy(id = "logout_sidebar_link")
	private WebElement logoutLink;

	public void logout() {
		click(menuButton);
		click(logoutLink);
	}
}