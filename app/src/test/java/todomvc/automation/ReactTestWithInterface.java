package todomvc.automation;

import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReactTestWithInterface {
    private static ChromeDriver driver;
    private static ReactPageWithInterface page;
    private static WebStorage webStorage;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        page = new ReactPageWithInterface(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        webStorage = (WebStorage) new Augmenter().augment(driver);
    }

    @BeforeEach
    void loadListPage() {
        page.navigate();
    }

    @DisplayName("Create new todo for plain text, single char, accented chars and emojis")
    @ParameterizedTest(name = "Creates new todo with text {0}")
    @CsvSource({
            "Make tests",
            "X",
            "Héłlö",
            "⭐"
            })
    void testNewTodos(String text) {
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
    void testTickingOffTodos(int todoIndex) {
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
        page.clickClearCompleted();
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
    void testStatusBarDisplay(String status, int numberOfSelected) {
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

    @Test
    // Uses JSExecutor in Page
    void shouldCheckLocalStorageForKey() {
        // Arrange - assert that new page does not have react-todos key and then add item
        boolean doesNotHaveKey = page.doesLocalStorageContainKey("react-todos");
        assertFalse(doesNotHaveKey);
        page.addNewTodoItem("Make tests");
        // Act
        boolean hasKey = page.doesLocalStorageContainKey("react-todos");
        // Assert
        assertTrue(hasKey);
    }

    @Test
    // This test is completely in this file and does not use the companion page file
    void ShouldCheckThatEachTodoHasARecordInLocaleStorageWithoutUsingPage() {
        for (int i = 1; i <= 5; i ++) {
            page.addNewTodoItem("I am todo number " + i);
        }
        LocalStorage localStorage = webStorage.getLocalStorage();
        System.out.println("This is local storage: " + localStorage);
        localStorage.getItem("react-todos");
        String reactTodosJson = localStorage.getItem("react-todos");
        Gson gson = new Gson(); // You need the Gson library for this
        List<Object> todos = gson.fromJson(reactTodosJson, List.class);
        Object firstTodo = todos.get(0);
        System.out.println("Todos: " + todos);
        System.out.println(todos.size());

    }
    @Test
    // This test is equivalent to the above test but DOES use the companion page file
    void ShouldCheckThatEachTodoHasARecordInLocaleStorageUsingPage() {
        for (int i = 1; i <= 5; i ++) {
            page.addNewTodoItem("I am todo number " + i);
        }
        assertEquals(5, page.checkNumberOfTodosInLocalStorage());
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
