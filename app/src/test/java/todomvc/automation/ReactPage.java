package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ReactPage {
    protected WebDriver driver;
    private final By newTodoInputBy = By.cssSelector("input[class='new-todo']");
    private final By firstTodoBy = By.xpath("/html/body/section/div/section/ul/li/div/label");

    private final By firstTodoEditBy = By.cssSelector(".edit");
    public ReactPage(WebDriver driver) {this.driver = driver;}
    public void navigate() {
        driver.get("https://todomvc.com/examples/react/#/");
    }
    public void addNewTodoItem() {
        WebElement newTodoInput = driver.findElement(newTodoInputBy);
        newTodoInput.sendKeys("Make tests");
        newTodoInput.sendKeys(Keys.ENTER);
    }
    public String getFirstTodo() {
        WebElement firstTodo = driver.findElement(firstTodoBy);
        return firstTodo.getText();
    }
    public void modifyFirstTodo(int todoLength) {
        WebElement firstTodo = driver.findElement(firstTodoBy);

        Actions actions = new Actions(driver);
        actions.doubleClick(firstTodo).perform();

        WebElement firstTodoEdit = driver.findElement(firstTodoEditBy);
        for (int i = 1; i <= todoLength; i++) { firstTodoEdit.sendKeys(Keys.BACK_SPACE); }
        firstTodoEdit.sendKeys("Break tests");
        firstTodoEdit.sendKeys(Keys.ENTER);
    }


}
