package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    int todoIndex = 1;
    page.navigate();
    page.addNewTodoItem("Make tests");
    assertEquals("Make tests", page.getTodo(todoIndex));
    }

    @Test
    void shouldModifyFirstTodo() {
    ReactPage page = new ReactPage(driver);
    int todoIndex = 1;
    page.navigate();
        page.addNewTodoItem("Make tests");
    int todoLength = page.getTodo(todoIndex).length();
    page.modifyATodo(todoLength, todoIndex);
    assertEquals("Break tests", page.getTodo(todoIndex));
    }

    @DisplayName("Ticking off todos")
    @ParameterizedTest(name = "Ticks off todo number {0}")
    @CsvSource({
            "1",
            "2",
            "3"
    })
    void shouldTickOffTodoItems(int todoIndex) {
        ReactPage page = new ReactPage(driver);
        page.navigate();
        page.addNewTodoItem("Make tests");
        page.addNewTodoItem("Make more tests");
        page.addNewTodoItem("Tests tests tests");
        page.tickOffATodoItem(todoIndex);
        System.out.println("Ticking off test with index " + todoIndex);

        assertTrue(page.checkTodoIsCompleted(todoIndex));
    }

    @Test
    void shouldClearAllItemsWhenClearCompletedIsClicked() {
        ReactPage page = new ReactPage(driver);
        page.navigate();
        page.addNewTodoItem("Make tests");
        page.addNewTodoItem("Make more tests");
        page.addNewTodoItem("Tests tests tests");

        page.tickOffATodoItem(1);
        page.tickOffATodoItem(3);

        page.clearCompleted();
        assertEquals("Make more tests", page.getTodo(1));
    }


    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}
