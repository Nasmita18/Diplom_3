package stellar.burger.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PasswordRecoveryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    private By emailInput = By.name("name");
    private By submitButton = By.xpath("//button[text()='Восстановить']");

    private By recoveryCodeInput = By.xpath("//input[@name='name']");
    private By newPasswordInput = By.xpath("//input[@name='Введите новый пароль']");
    private By resetPasswordButton = By.xpath("//button[text()='Сохранить']");

    private By loginLink = By.xpath("//a[text()='Войти']");

    public PasswordRecoveryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(email);
    }

    public void clickSubmitButton() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(submitButton));
        button.click();
    }

    public void enterRecoveryCode(String code) {
        WebElement codeField = wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryCodeInput));
        codeField.sendKeys(code);
    }

    public void enterNewPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(newPasswordInput));
        passwordField.sendKeys(password);
    }

    public void clickResetPasswordButton() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(resetPasswordButton));
        button.click();
    }

    public void clickLoginLink() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(loginLink));
        link.click();
    }
}
