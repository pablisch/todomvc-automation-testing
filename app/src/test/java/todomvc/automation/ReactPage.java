package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;


public class ReactPage {

    protected WebDriver driver;

    private final By newTodoInputBy = By.cssSelector("input[class='new-todo']");

    private final By todoEditBy = By.cssSelector(".edit");

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

    // Format By for indexed todo item
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

    public String getTodo(int index) {
        WebElement todo = driver.findElement(getTodoLabelSelector(index));
        System.out.println(todo.getText());
        return todo.getText();
    }

    public void modifyATodo(int todoLength, int index) {
        WebElement todo = driver.findElement(getTodoLabelSelector(index));

        Actions actions = new Actions(driver);
        actions.doubleClick(todo).perform();

        WebElement todoEdit = driver.findElement(todoEditBy);
        for (int i = 1; i <= todoLength; i++) {
            todoEdit.sendKeys(Keys.BACK_SPACE);
        }
        todoEdit.sendKeys("Break tests");
        todoEdit.sendKeys(Keys.ENTER);
    }

        // Format By for indexed checkbox selector
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
