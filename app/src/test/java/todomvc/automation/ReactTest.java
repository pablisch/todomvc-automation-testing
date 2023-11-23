package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    page.addNewTodoItem();
    assertEquals("Make tests", page.getFirstTodo());
    }

    @Test
    void shouldModifyFirstTodo() {
    ReactPage page = new ReactPage(driver);
    page.navigate();
    page.addNewTodoItem();
    int todoLength = page.getFirstTodo().length();
    page.modifyFirstTodo(todoLength);
    assertEquals("Break tests", page.getFirstTodo());
    }

    @Test
    void shouldTickOffFirstTodoItem() {
        ReactPage page = new ReactPage(driver);
        page.navigate();
        page.addNewTodoItem();
        page.tickOffFirstTodoItem();
        page.checkFirstTodoIsCompleted();
//        WebElement completedTodo = page.getFirstTodoItemCompleted();

//        assertTrue(completedTodo.isDisplayed());

    }

    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}
