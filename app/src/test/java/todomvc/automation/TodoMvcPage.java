package todomvc.automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;

public interface TodoMvcPage {
     By newTodoInputBy = By.cssSelector("input[class='new-todo']");
     final By todoEditBy = By.cssSelector("input[class='edit']");
     final By clearCompletedButtonBy = By.cssSelector("button[class='clear-completed']");
     final By clickToggleAllArrowBy = By.cssSelector("label[for='toggle-all']");
     final By numberOfActiveItemsBy = By.cssSelector("span[class='todo-count']");
     final By mainSectionFooterBy = By.cssSelector("footer[class='footer']");
     final String todoLabelSelectorBaseTakesIndex = ".todo-list li:nth-child(%d) label";
     final String getTodoCheckboxSelectorBaseTakesIndex = ".todo-list li:nth-child(%d) input.toggle";
     final String todoListItemSelectorBaseTakesIndex = ".todo-list li:nth-child(%d)";

    // Format By for indexed todo item (label text)
    By getTodoLabelSelector(int index);

    // Format By for indexed todo checkbox selector
    By getTodoCheckboxSelector(int index);

    public void takeScreenshot(String desiredPath) throws IOException;

    public void navigate();

    public void addNewTodoItem(String todoValue);

    public String getTodoText(int index);

    public void modifyATodo(int todoLength, int index);

    public void tickOffATodoItem(int index);

    public Boolean checkTodoIsCompleted(int index);

    public void clickClearCompleted();

    public void clickToggleAllArrow();

    public String getNumberOfActiveItems();

    public String checkDisplayStateOfMainSectionFooter();

}
