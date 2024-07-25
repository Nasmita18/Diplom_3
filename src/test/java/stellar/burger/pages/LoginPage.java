package stellar.burger.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    private By emailInput = By.name("name");
    private By passwordInput = By.name("Пароль");
    private By loginSubmitButton = By.xpath("//button[text()='Войти']");

    private By personalAccountLink = By.xpath("//*[@id=\"root\"]/div/header/nav/a");

    private By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private By clickRegisterLink = By.xpath("//a[text()='Зарегистрироваться']");

    private By passwordRecoveryLink = By.xpath("//a[text()='Восстановить пароль']");
    private By loginFromPasswordRecoveryLink = By.xpath("//a[text()='Войти']");

    private By logoutButton = By.xpath("//button[text()='Выход']");


    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.jsExecutor = (JavascriptExecutor) driver; // Инициализация jsExecutor
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordField.sendKeys(password);
    }

    public void clickLoginSubmitButton() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(loginSubmitButton));
        button.click();
    }

    public void clickPersonalAccountLink() {
        WebElement accountLink = wait.until(ExpectedConditions.presenceOfElementLocated(personalAccountLink));
        accountLink.click();
    }

    public boolean isPersonalAccountLinkVisible() {
        WebElement accountLink = wait.until(ExpectedConditions.visibilityOfElementLocated(personalAccountLink));
        return accountLink.isDisplayed();
    }

    public void scrollToRegisterButton() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(registerLink));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", button);
    }

    public void clickRegisterButton() {
        WebElement registerLink = wait.until(ExpectedConditions.presenceOfElementLocated(clickRegisterLink));
        registerLink.click();
    }

    public void scrollToPasswordRecoveryLink() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(passwordRecoveryLink));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", link);
    }

    public void clickPasswordRecoveryLink() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(passwordRecoveryLink));
        link.click();
    }

    public void clickLoginFromPasswordRecoveryLink() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(loginFromPasswordRecoveryLink));
        link.click();
    }

    public void clickLogoutButton() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButton));
        link.click();
    }
}