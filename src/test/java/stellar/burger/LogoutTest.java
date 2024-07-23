package stellar.burger;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellar.burger.helper.RegistrationHelper;
import stellar.burger.pages.LoginPage;
import stellar.burger.pages.MainPage;
import stellar.burger.pages.RegistrationPage;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LogoutTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        // Настройка базового URI для RestAssured
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        // Инициализация WebDriver и WebDriverWait
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Открытие главной страницы
        driver.get("https://stellarburgers.nomoreparties.site/");

        // Инициализация страниц
        mainPage = new MainPage(driver, wait);
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testLogoutFromPersonalAccount() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456"; // Минимальная длина пароля — 6 символов
        String name = "TestUser";

        // Регистрация пользователя
        mainPage.clickLoginButton();
        RegistrationHelper.createUser(email, name, password);

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        // Вход
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmitButton();

        // Переход в личный кабинет
        loginPage.clickPersonalAccountLink();

        // Проверка открытия личного кабинета
        wait.until(ExpectedConditions.urlContains("/account/profile"));
        assertTrue(loginPage.isPersonalAccountLinkVisible());

        // Нажатие на кнопку «Выйти»
        loginPage.clickLogoutButton();

        // Проверка перехода на страницу входа
        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @After
    public void tearDown() {
        RegistrationHelper.removeUser();
        // Закрытие браузера после завершения тестов
        if (driver != null) {
            driver.quit();
        }
    }
}
