package stellar.burger;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellar.burger.pages.MainPage;

import java.time.Duration;

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
        // Переход к разделу "Начинки"
        mainPage.clickFillingsTab();

        // Ожидание появления элемента с заголовком "Начинки"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Начинки']")));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Начинки']")).isDisplayed());
    }

    @Test
    public void testNavigateToSaucesSection() {
        // Переход к разделу "Соусы"
        mainPage.clickSaucesTab();

        // Ожидание появления элемента с заголовком "Соусы"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Соусы']")));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Соусы']")).isDisplayed());
    }

    @Test
    public void testNavigateToBunsSection() {
        // Переход к разделу "Начинки"
        mainPage.clickFillingsTab();

        // Ожидание появления элемента с заголовком "Начинки"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Начинки']")));

        // Переход к разделу "Булки"
        mainPage.clickBunsTab();

        // Ожидание появления элемента с заголовком "Булки"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Булки']")));
        assertTrue(driver.findElement(By.xpath("//h2[text()='Булки']")).isDisplayed());
    }

    @After
    public void tearDown() {
        // Закрытие браузера после завершения тестов
        if (driver != null) {
            driver.quit();
        }
    }
}
