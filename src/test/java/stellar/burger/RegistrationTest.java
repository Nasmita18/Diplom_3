package stellar.burger;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
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

public class RegistrationTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://stellarburgers.nomoreparties.site/");

        mainPage = new MainPage(driver, wait);
        loginPage = new LoginPage(driver, wait);
        registrationPage = new RegistrationPage(driver, wait);
    }

    @Test
    public void testSuccessfulRegistrationAndLogin() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456";
        String name = "TestUser";

        // Переход на страницу авторизации
        mainPage.clickLoginButton();

        // Переход на страницу регистрации
        loginPage.scrollToRegisterButton();
        loginPage.clickRegisterButton();

        // Заполнение формы регистрации
        registrationPage.enterName(name);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);

        // Завершение регистрации
        registrationPage.scrollToRegisterButton();
        registrationPage.clickRegisterButton();

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        //получаем аксестокен
        accessToken = RegistrationHelper.getAccessToken(email, password, name);

        // Ввод логина и пароля
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmitButton();

        // Проверка видимости ссылки на личный кабинет
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        assertTrue(loginPage.isPersonalAccountLinkVisible());

        // Переход в личный кабинет
        loginPage.clickPersonalAccountLink();

        // Проверка открытия личного кабинета
        wait.until(ExpectedConditions.urlContains("/account/profile"));
        assertTrue(loginPage.isPersonalAccountLinkVisible());
    }

    @Test
    public void testRegistrationWithInvalidPassword() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String invalidPassword = "12345"; // Пароль менее 6 символов
        String name = "TestUser";

        // Переход на страницу авторизации
        mainPage.clickLoginButton();

        // Переход на страницу регистрации
        loginPage.scrollToRegisterButton();
        loginPage.clickRegisterButton();

        // Заполнение формы регистрации
        registrationPage.enterName(name);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(invalidPassword);

        // Переход на страницу регистрации
        registrationPage.scrollToRegisterButton();
        registrationPage.clickRegisterButton();

        // Проверка сообщения об ошибке
        assertTrue(registrationPage.isErrorMessageVisible());
    }

    @After
    public void tearDown() {
        // Удаление зарегистрированного пользователя, если он существует
        RegistrationHelper.removeUser(accessToken);

        if (driver != null) {
            driver.quit();
        }
    }
}
