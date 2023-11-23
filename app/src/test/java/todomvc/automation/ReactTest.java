package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReactTest {
    private static ChromeDriver driver;

    @BeforeEach
    void launchBrowser() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }


    @Test
    void shouldCreateNewTodoWithTextMakeTests() {
    ReactPage page = new ReactPage(driver);
    page.navigate();
    page.addNewTodoItem("Make tests");
    assertEquals("Make tests", page.getFirstTodo(1));
    }

    @Test
    void shouldModifyFirstTodo() {
    ReactPage page = new ReactPage(driver);
    page.navigate();
        page.addNewTodoItem("Make tests");
    int todoLength = page.getFirstTodo(1).length();
    page.modifyATodo(todoLength);
    assertEquals("Break tests", page.getFirstTodo(1));
    }

    @Test
    void shouldTickOffFirstTodoItem() {
        int todoIndex = 1;
        ReactPage page = new ReactPage(driver);
        page.navigate();
        page.addNewTodoItem("Make tests");
        page.tickOffATodoItem(todoIndex);

        assertTrue(page.checkTodoIsCompleted(todoIndex));
    }

    @Test
    void shouldTickOffSecondTodoItem() {
        ReactPage page = new ReactPage(driver);
        int todoIndex = 2;
        page.navigate();
        page.addNewTodoItem("Make tests");
        page.addNewTodoItem("Make more tests");
        page.tickOffATodoItem(todoIndex);

        assertTrue(page.checkTodoIsCompleted(todoIndex));
    }


    @AfterEach
    void closeBrowser() {
        driver.quit();
    }


}
