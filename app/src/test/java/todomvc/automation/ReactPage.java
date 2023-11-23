package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ReactPage {

    protected WebDriver driver;

    int index = 1;
    String todoCheckboxSelector = String.format(".todo-list li:nth-child(%d) input.toggle", index );
    private By todoCheckboxSelectorBy = By.cssSelector(todoCheckboxSelector);
//    private final By todoCheckboxSelectorBy = By.cssSelector(".todo-list li:nth-child(1) input.toggle");

    String todoListItemSelector = String.format(".todo-list li:nth-child(%d)", index );
    private By todoListItemSelectorBy = By.cssSelector(todoListItemSelector);


    private final By newTodoInputBy = By.cssSelector("input[class='new-todo']");
    private final By firstTodoBy = By.xpath("/html/body/section/div/section/ul/li/div/label");

    private final By firstTodoEditBy = By.cssSelector(".edit");

//    private final By IndexedTodoListItemBy = By.cssSelector(".todo-list li:nth-child(1)");


    public ReactPage(WebDriver driver) {
        this.driver = driver;
    }

    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception {
        TakesScreenshot screenshot = ((TakesScreenshot)webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }

    public void navigate() {
        driver.get("https://todomvc.com/examples/react/#/");
    }

    public void addNewTodoItem(String todoValue) {
        WebElement newTodoInput = driver.findElement(newTodoInputBy);
        newTodoInput.sendKeys(todoValue);
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
        for (int i = 1; i <= todoLength; i++) {
            firstTodoEdit.sendKeys(Keys.BACK_SPACE);
        }
        firstTodoEdit.sendKeys("Break tests");
        firstTodoEdit.sendKeys(Keys.ENTER);
    }

    public void tickOffFirstTodoItem() {
        WebElement todoCheckbox = driver.findElement(todoCheckboxSelectorBy);
        todoCheckbox.click();
    }

    public Boolean checkFirstTodoIsCompleted() {
        WebElement todoCheckbox = driver.findElement(todoListItemSelectorBy);
        String todoCheckboxClass = todoCheckbox.getAttribute("class");
        System.out.println(todoCheckboxClass);
        return todoCheckboxClass.equals("completed");
    }

}
