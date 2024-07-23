package stellar.burger.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    private By nameInput = By.xpath("//input[@name='name' and preceding-sibling::label[text()='Имя']]");
    private By emailInput = By.xpath("//input[@name='name' and preceding-sibling::label[text()='Email']]");
    private By passwordInput = By.xpath("//input[@name='Пароль' and preceding-sibling::label[text()='Пароль']]");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By errorMessage = By.xpath("//p[contains(text(), 'Некорректный пароль')]");

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void enterName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordField.sendKeys(password);
    }

    public void clickRegisterButton() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(registerButton));
        button.click();
    }

    public boolean isErrorMessageVisible() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return error.isDisplayed();
    }

    public void scrollToRegisterButton() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(registerButton));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", button);
    }
}
