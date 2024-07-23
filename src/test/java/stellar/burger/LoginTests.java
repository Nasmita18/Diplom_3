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
import stellar.burger.pages.PasswordRecoveryPage;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LoginTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private LoginPage loginPage;
    private PasswordRecoveryPage passwordRecoveryPage;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://stellarburgers.nomoreparties.site/");

        mainPage = new MainPage(driver, wait);
        loginPage = new LoginPage(driver, wait);
        passwordRecoveryPage = new PasswordRecoveryPage(driver, wait);
    }

    @Test
    public void testLoginFromMainPage() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456";
        String name = "TestUser";

        // Регистрация пользователя
        mainPage.clickLoginButton();
        RegistrationHelper.createUser(email, name, password);

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        // Вход через кнопку «Войти в аккаунт» на главной
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmitButton();

        // Проверка видимости ссылки на личный кабинет
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        assertTrue(loginPage.isPersonalAccountLinkVisible());
    }

    @Test
    public void testLoginFromPersonalAccountLink() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456"; // Минимальная длина пароля — 6 символов
        String name = "TestUser";

        // Регистрация пользователя
        mainPage.clickLoginButton();
        RegistrationHelper.createUser(email, name, password);

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        // Вход через кнопку «Личный кабинет»
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmitButton();

        loginPage.clickPersonalAccountLink();

        // Проверка открытия личного кабинета
        wait.until(ExpectedConditions.urlContains("/account/profile"));
        assertTrue(loginPage.isPersonalAccountLinkVisible());
    }

    @Test
    public void testLoginFromRegistrationForm() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456"; // Минимальная длина пароля — 6 символов
        String name = "TestUser";

        // Регистрация пользователя
        mainPage.clickLoginButton();
        RegistrationHelper.createUser(email, name, password);

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        // Вход через кнопку в форме регистрации
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmitButton();

        // Проверка видимости ссылки на личный кабинет
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        assertTrue(loginPage.isPersonalAccountLinkVisible());
    }

    @Test
    public void testLoginFromPasswordRecoveryForm() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456"; // Минимальная длина пароля — 6 символов
        String name = "TestUser";
        String recoveryCode = "123456"; // Пример кода из письма

        // Регистрация пользователя
        mainPage.clickLoginButton();
        RegistrationHelper.createUser(email, name, password);

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        // Переход к форме восстановления пароля и запрос кода
        loginPage.scrollToPasswordRecoveryLink();
        loginPage.clickPasswordRecoveryLink();
        passwordRecoveryPage.enterEmail(email);
        passwordRecoveryPage.clickSubmitButton();

        // Ожидание перехода на страницу восстановления пароля
        wait.until(ExpectedConditions.urlContains("/reset-password"));

        // Ввод кода из письма и нового пароля
        passwordRecoveryPage.enterRecoveryCode(recoveryCode);
        passwordRecoveryPage.enterNewPassword(password);
        passwordRecoveryPage.clickResetPasswordButton();

        // Вход через кнопку в форме восстановления пароля
        passwordRecoveryPage.clickLoginLink();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmitButton();

        // Проверка видимости ссылки на личный кабинет
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        assertTrue(loginPage.isPersonalAccountLinkVisible());
    }


    @After
    public void tearDown() {
        RegistrationHelper.removeUser();
        if (driver != null) {
            driver.quit();
        }
    }
}