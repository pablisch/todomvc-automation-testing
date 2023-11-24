package todomvc.automation;

import org.openqa.selenium.*;

public class VuePage {
    protected WebDriver driver;
    private final By mainSectionFooterBy = By.cssSelector("footer[class='footer']");
//    private final By mainSectionFooterBy = By.cssSelector("footer[class='footer'][style='display:none;']");

    public VuePage(WebDriver driver) {
        this.driver = driver;
    }

    // Format By for indexed todo item (label text)


    public void navigate() {
        driver.get("https://todomvc.com/examples/vue");
    }


    public String checkDisplayStateOfMainSectionFooter() {
        WebElement mainSectionFooter = driver.findElement(mainSectionFooterBy);
        return mainSectionFooter.getAttribute("style");
    }

}
