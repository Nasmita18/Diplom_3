package stellar.burger.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Методы для взаимодействия с элементами главной страницы
    public void clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Войти в аккаунт']")));
        loginButton.click();
    }

    public void clickConstructorLink() {
        WebElement constructorLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"root\"]/div/header/nav/ul/li[1]/a")));
        constructorLink.click();
    }

    public void clickLogo() {
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"root\"]/div/header/nav/div")));
        logo.click();
    }

    public boolean isConstructorPage() {
        return driver.getCurrentUrl().contains("/");
    }

    // Метод для нажатия на раздел "Начинки"
    public void clickFillingsTab() {
        driver.findElement(By.xpath("//span[text()='Начинки']")).click();
    }

    // Метод для нажатия на раздел "Соусы"
    public void clickSaucesTab() {
        driver.findElement(By.xpath("//span[text()='Соусы']")).click();
    }

    // Метод для нажатия на раздел "Булки"
    public void clickBunsTab() {
        driver.findElement(By.xpath("//span[text()='Булки']")).click();
    }
}
