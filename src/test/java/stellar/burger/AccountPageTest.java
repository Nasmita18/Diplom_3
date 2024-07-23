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

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class AccountPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private LoginPage loginPage;
//    private RegistrationPage registrationPage;
//
//    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://stellarburgers.nomoreparties.site/");

        mainPage = new MainPage(driver, wait);
        loginPage = new LoginPage(driver, wait);
//        registrationPage = new RegistrationPage(driver, wait);
    }

    @Test
    public void testAccessAccountPageFromPersonalAccountLink() {
        // Уникальные данные пользователя
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "123456"; // Минимальная длина пароля — 6 символов
        String name = "TestUser";

        // Регистрация пользователя
        mainPage.clickLoginButton();
        RegistrationHelper.createUser(email, name, password);

        // Ожидание перехода на страницу авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        // Вход в систему
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
        assertTrue(driver.getCurrentUrl().contains("/account/profile"));
    }

    @After
    public void tearDown() {
        RegistrationHelper.removeUser();
        if (driver != null) {
            driver.quit();
        }
    }
}
