package todomvc.automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

public interface TodoMvcPage {
     WebDriver driver = null;
     final By newTodoInputBy = By.cssSelector("input[class='new-todo']");
     final By todoEditBy = By.cssSelector("input[class='edit']");
     final By clearCompletedButtonBy = By.cssSelector("button[class='clear-completed']");
     final By clickToggleAllArrowBy = By.cssSelector("label[for='toggle-all']");
     final By numberOfActiveItemsBy = By.cssSelector("span[class='todo-count']");
     final By mainSectionFooterBy = By.cssSelector("footer[class='footer']");

    public TodoMvcPage(WebDriver driver) {
        this.driver = driver;
    }

    // Format By for indexed todo item (label text)
    default By getTodoLabelSelector(int index) {
        String todoLabelSelector = String.format(".todo-list li:nth-child(%d) label", index );
        By todoLabelSelectorBy;
        todoLabelSelectorBy = By.cssSelector(todoLabelSelector);
        return todoLabelSelectorBy;
    }

    // Format By for indexed todo checkbox selector
    default By getTodoCheckboxSelector(int index) {
        String todoCheckboxSelector = String.format(".todo-list li:nth-child(%d) input.toggle", index );
        By todoCheckboxSelectorBy = By.cssSelector(todoCheckboxSelector);
        return todoCheckboxSelectorBy;
    }

    default public void takeScreenshot(String desiredPath) throws Exception {
        TakesScreenshot screenshot = ((TakesScreenshot)this.driver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }

    default public void navigate() {
        driver.get("https://todomvc.com/examples/react/#/");
    }

    default public void addNewTodoItem(String todoValue) {
        WebElement newTodoInput = driver.findElement(newTodoInputBy);
        newTodoInput.sendKeys(todoValue);
        newTodoInput.sendKeys(Keys.ENTER);
    }

    default public String getTodoText(int index) {
        WebElement todo = driver.findElement(getTodoLabelSelector(index));
        return todo.getText();
    }

    default public void modifyATodo(int todoLength, int index) {
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

    default public void tickOffATodoItem(int index) {
        By thisCheckboxSelectorBy = getTodoCheckboxSelector(index);
        WebElement todoCheckbox = driver.findElement(thisCheckboxSelectorBy);
        todoCheckbox.click();
    }

    default public Boolean checkTodoIsCompleted(int index) {
        String todoListItemSelector = String.format(".todo-list li:nth-child(%d)", index );
        By todoListItemSelectorBy = By.cssSelector(todoListItemSelector);
//        By thisCheckboxSelectorBy = getTodoCheckboxSelector(index);
        WebElement todoListItem = driver.findElement(todoListItemSelectorBy);
        String todoListItemClass = todoListItem.getAttribute("class");
        return todoListItemClass.equals("completed");
    }

    default public void clickClearCompleted() {
        WebElement clearCompletedButton = driver.findElement(clearCompletedButtonBy);
        clearCompletedButton.click();
    }

    default public void clickToggleAllArrow() {
        WebElement clickToggleAllArrow = driver.findElement(clickToggleAllArrowBy);
        clickToggleAllArrow.click();
    }

    default public String getNumberOfActiveItems() {
        WebElement numberOfActiveItems = driver.findElement(numberOfActiveItemsBy);
        return numberOfActiveItems.getText();
    }

//    public String checkDisplayStateOfMainSectionFooter() {
//        WebElement mainSectionFooter = driver.findElement(mainSectionFooterBy);
//        return mainSectionFooter.getAttribute("style");
//    }

}
