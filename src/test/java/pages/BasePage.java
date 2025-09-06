package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.DriverFactory;

public class BasePage {
    protected WebDriver driver;
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }
    protected void click(WebElement element) {
        element.click();
        waitFor(1);
    }
    protected void type(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        waitFor(1);
    }
    protected String getText(WebElement element) {
        waitFor(1);
        return element.getText();
    }
    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}