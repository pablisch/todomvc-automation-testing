package todomvc.automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;

public class ReactPageWithInterface implements TodoMvcPage {
    protected WebDriver driver;
//    private final By newTodoInputBy = By.cssSelector("input[class='new-todo']");
//    private final By todoEditBy = By.cssSelector("input[class='edit']");
//    private final By clearCompletedButtonBy = By.cssSelector("button[class='clear-completed']");
//    private final By clickToggleAllArrowBy = By.cssSelector("label[for='toggle-all']");
//    private final By numberOfActiveItemsBy = By.cssSelector("span[class='todo-count']");
//    private final By mainSectionFooterBy = By.cssSelector("footer[class='footer']");

    public ReactPageWithInterface(WebDriver driver) {
        this.driver = driver;
    }

    // Format By for indexed todo item (label text)
    @Override
    public By getTodoLabelSelector(int index) {
        String todoLabelSelector = String.format(".todo-list li:nth-child(%d) label", index );
        By todoLabelSelectorBy;
        todoLabelSelectorBy = By.cssSelector(todoLabelSelector);
        return todoLabelSelectorBy;
    }

    // Format By for indexed todo checkbox selector
    @Override
    public By getTodoCheckboxSelector(int index) {
        String todoCheckboxSelector = String.format(".todo-list li:nth-child(%d) input.toggle", index );
        By todoCheckboxSelectorBy = By.cssSelector(todoCheckboxSelector);
        return todoCheckboxSelectorBy;
    }
    @Override
    public void takeScreenshot(String desiredPath) throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot)this.driver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
    @Override
    public void navigate() {
        driver.get("https://todomvc.com/examples/react/#/");
    }
    @Override
    public void addNewTodoItem(String todoValue) {
        WebElement newTodoInput = driver.findElement(newTodoInputBy);
        newTodoInput.sendKeys(todoValue);
        newTodoInput.sendKeys(Keys.ENTER);
    }
    @Override
    public String getTodoText(int index) {
        WebElement todo = driver.findElement(getTodoLabelSelector(index));
        return todo.getText();
    }
    @Override
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
    @Override
    public void tickOffATodoItem(int index) {
        By thisCheckboxSelectorBy = getTodoCheckboxSelector(index);
        WebElement todoCheckbox = driver.findElement(thisCheckboxSelectorBy);
        todoCheckbox.click();
    }
    @Override
    public Boolean checkTodoIsCompleted(int index) {
        String todoListItemSelector = String.format(".todo-list li:nth-child(%d)", index );
        By todoListItemSelectorBy = By.cssSelector(todoListItemSelector);
//        By thisCheckboxSelectorBy = getTodoCheckboxSelector(index);
        WebElement todoListItem = driver.findElement(todoListItemSelectorBy);
        String todoListItemClass = todoListItem.getAttribute("class");
        return todoListItemClass.equals("completed");
    }
    @Override
    public void clickClearCompleted() {
        WebElement clearCompletedButton = driver.findElement(clearCompletedButtonBy);
        clearCompletedButton.click();
    }

    @Override
    public void clickToggleAllArrow() {
        WebElement clickToggleAllArrow = driver.findElement(clickToggleAllArrowBy);
        clickToggleAllArrow.click();
    }

    @Override
    public String getNumberOfActiveItems() {
        WebElement numberOfActiveItems = driver.findElement(numberOfActiveItemsBy);
        return numberOfActiveItems.getText();
    }

    @Override
    public String checkDisplayStateOfMainSectionFooter() {
        WebElement mainSectionFooter = driver.findElement(mainSectionFooterBy);
        return mainSectionFooter.getAttribute("style");
    }

}
