package stellar.burger;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellar.burger.pages.MainPage;

import java.time.Duration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConstructorSectionsTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    @Before
    public void setUp() {
        // Настройка базового URI для RestAssured
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        // Инициализация WebDriver и WebDriverWait
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Открытие главной страницы
        driver.get("https://stellarburgers.nomoreparties.site/");
        // Инициализация MainPage
        mainPage = new MainPage(driver, wait);
    }

    @Test
    public void testNavigateToFillingsSection() {
        // смотрим что начальное состояние начинки неактивно
        WebElement fillingsTab = driver.findElement(By.xpath("//span[text()='Начинки']/.."));
        String initialClass = fillingsTab.getAttribute("class");
        assertFalse(initialClass.contains("tab_tab_type_current__2BEPc"));

        // Переходим к разделу нажчинки
        mainPage.clickFillingsTab();

        // ждем появления элемента с заголовком начинки
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Начинки']")));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Начинки']")).isDisplayed());

        // смотрим что раздел начинки активен
        String finalClass = fillingsTab.getAttribute("class");
        assertTrue("Expected 'Начинки' tab to be active", finalClass.contains("tab_tab_type_current__2BEPc"));
    }

    @Test
    public void testNavigateToSaucesSection() {
        WebElement saucesTab = driver.findElement(By.xpath("//span[text()='Соусы']/.."));
        String initialClass = saucesTab.getAttribute("class");
        assertFalse( initialClass.contains("tab_tab_type_current__2BEPc"));
        // Переход к разделу "Соусы"
        mainPage.clickSaucesTab();

        // Ожидание появления элемента с заголовком "Соусы"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Соусы']")));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Соусы']")).isDisplayed());

        String finalClass = saucesTab.getAttribute("class");
        assertTrue("Expected 'Булки' tab to be active", finalClass.contains("tab_tab_type_current__2BEPc"));
    }

    @Test
    public void testNavigateToBunsSection() {
        mainPage.clickSaucesTab();
        // смотрим что начальное состояние булки неактивно
        WebElement bunsTab = driver.findElement(By.xpath("//span[text()='Булки']/.."));
        String initialClass = bunsTab.getAttribute("class");
        assertFalse(initialClass.contains("tab_tab_type_current__2BEPc"));

        // Переходим к разделу "Булки"
        mainPage.clickBunsTab();

        // Ждем появления элемента "Булки"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Булки']")));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Булки']")).isDisplayed());

        // Смотрим, что раздел "Булки активен
        String finalClass = bunsTab.getAttribute("class");
        assertTrue("Expected 'Булки' tab to be active", finalClass.contains("tab_tab_type_current__2BEPc"));
    }

    @After
    public void tearDown() {
        // Закрытие браузера после завершения тестов
        if (driver != null) {
            driver.quit();
        }
    }
}
