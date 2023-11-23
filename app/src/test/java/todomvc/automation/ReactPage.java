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
//    String todoCheckboxSelector = String.format(".todo-list li:nth-child(%d) input.toggle", index );
//    private By todoCheckboxSelectorBy = By.cssSelector(todoCheckboxSelector);
//    private final By todoCheckboxSelectorBy = By.cssSelector(".todo-list li:nth-child(1) input.toggle");

    String todoLabelSelector = String.format(".todo-list li:nth-child(%d) label", index );
    private By todoLabelSelectorBy = By.cssSelector(todoLabelSelector);



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

    By getTodoLabelSelector(int index) {
        String todoLabelSelector = String.format(".todo-list li:nth-child(%d) label", index );
        By todoLabelSelectorBy;
        todoLabelSelectorBy = By.cssSelector(todoLabelSelector);
        return todoLabelSelectorBy;
    }

    public void addNewTodoItem(String todoValue) {
        WebElement newTodoInput = driver.findElement(newTodoInputBy);
        newTodoInput.sendKeys(todoValue);
        newTodoInput.sendKeys(Keys.ENTER);
    }

    public String getFirstTodo(int index) {
//        WebElement firstTodo = driver.findElement(firstTodoBy);
        WebElement firstTodo = driver.findElement(getTodoLabelSelector(index));
//        System.out.println(firstTodo.getText());
        System.out.println(firstTodo.getText());

        return firstTodo.getText();
    }

    public void modifyATodo(int todoLength) {
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

        By getTodoCheckboxSelector(int index) {
        String todoCheckboxSelector = String.format(".todo-list li:nth-child(%d) input.toggle", index );
        By todoCheckboxSelectorBy = By.cssSelector(todoCheckboxSelector);
        return todoCheckboxSelectorBy;
    }

    public void tickOffATodoItem(int index) {
        By thisCheckboxSelectorBy = getTodoCheckboxSelector(index);
        WebElement todoCheckbox = driver.findElement(thisCheckboxSelectorBy);
        todoCheckbox.click();
    }

    public Boolean checkTodoIsCompleted(int index) {
        String todoListItemSelector = String.format(".todo-list li:nth-child(%d)", index );
        By todoListItemSelectorBy = By.cssSelector(todoListItemSelector);
//        By thisCheckboxSelectorBy = getTodoCheckboxSelector(index);
        WebElement todoListItem = driver.findElement(todoListItemSelectorBy);
        String todoListItemClass = todoListItem.getAttribute("class");
        System.out.println(todoListItemClass);
        return todoListItemClass.equals("completed");
    }

}
