package todomvc.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReactTest {
    private static ChromeDriver driver;
    private static ReactPage page;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        page = new ReactPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    @BeforeEach
    void loadListPage() {
        page.navigate();
    }


    @DisplayName("Create new todo")
    @ParameterizedTest(name = "Creates new todo with text {0}")
    @CsvSource({
            "Make tests",
            "X",
            "Héłlö",
            "⭐"
            })
    void shouldCreateNewTodoWithInputtedText(String text) {
        // Arrange
        int todoIndex = 1;
        // Act
        page.addNewTodoItem(text);
        // Assert
        assertEquals(text, page.getTodoText(todoIndex));
    }

    @Test
    void shouldNotCreateNewTodoWhenInputIsEmpty() {
        page.addNewTodoItem("I am a todo");
        assertEquals("1 item left", page.getNumberOfActiveItems());
        page.addNewTodoItem("");
        page.addNewTodoItem(" ");
        page.addNewTodoItem("        ");
        assertEquals("1 item left", page.getNumberOfActiveItems());
    }

    @Test
    void shouldModifyFirstTodo() {
        // Arrange
        int todoIndex = 1;
        page.addNewTodoItem("Make tests");
        // Act
        int todoLength = page.getTodoText(todoIndex).length();
        page.modifyATodo(todoLength, todoIndex);
        // Assert
        assertEquals("Break tests", page.getTodoText(todoIndex));
    }

    @DisplayName("Ticking off todos")
    @ParameterizedTest(name = "Ticks off todo number {0}")
    @CsvSource({
            "1",
            "2",
            "3"
    })
    void shouldTickOffTodoItems(int todoIndex) {
        // Arrange
        page.addNewTodoItem("Make tests");
        page.addNewTodoItem("Make more tests");
        page.addNewTodoItem("Tests tests tests");
        // Act
        page.tickOffATodoItem(todoIndex);
        // Assert
        assertTrue(page.checkTodoIsCompleted(todoIndex));
    }

    @Test
    void shouldClearAllItemsWhenClearCompletedIsClicked() {
        // Arrange
        page.addNewTodoItem("Make tests");
        page.addNewTodoItem("Make more tests");
        page.addNewTodoItem("Tests tests tests");
        page.tickOffATodoItem(1);
        page.tickOffATodoItem(3);
        // Act
        page.clearCompleted();
        // Assert
        assertEquals("Make more tests", page.getTodoText(1));
    }

    @Test
    void shouldToggleAllItemsCompletedIncompleteWhenToggleAllArrowIsClicked() {
        page.addNewTodoItem("Make tests");
        page.addNewTodoItem("Make more tests");
        page.addNewTodoItem("Tests tests tests");
        page.tickOffATodoItem(2);
        assertEquals("2 items left", page.getNumberOfActiveItems());
        page.clickToggleAllArrow();
        assertEquals("0 items left", page.getNumberOfActiveItems());
        page.clickToggleAllArrow();
        assertEquals("3 items left", page.getNumberOfActiveItems());
    }

    @Test
    void todosShouldNotBleedOverIntoOtherVariants() {
        page.addNewTodoItem("Make tests");
        assertEquals("1 item left", page.getNumberOfActiveItems());
        VuePage vuePage = new VuePage(driver);
        vuePage.navigate();
        assertEquals("display: none;", vuePage.checkDisplayStateOfMainSectionFooter());

    }

    @DisplayName("Status bar display")
    @ParameterizedTest(name = "Status bar displays {0} when {1} of 3 items are completed")
    @CsvSource({
            "0 items left, 3",
            "1 item left, 2",
            "2 items left, 1",
            "3 items left, 0",
    })
    void testSearchTerms(String status, int numberOfSelected) {
        // Arrange (create three todos)
        for (int i = 1; i <= 3; i ++) {
            page.addNewTodoItem("I am todo number " + i);
        }
        // Act (tick off <numberOfSelected> items as completed)
        for (int i = 1; i <= numberOfSelected; i ++) {
            page.tickOffATodoItem(i);
        }
        //Assert (status bar display)
        assertEquals(status, page.getNumberOfActiveItems());
    }

    @AfterEach
    void clearStorage() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("localStorage.removeItem('react-todos');");
        driver.navigate().refresh();
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
    }
