package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ReactTest {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
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

    int todoLength = page.getFirstTodo().length();
    page.modifyFirstTodo(todoLength);
    assertEquals("Make test", page.getFirstTodo());
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
}
