package com.dev.manto_sagrado.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

@ActiveProfiles("test")
public class ClientUITest {
    private final static String DEFAULT_URL = "http://localhost:3000";

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Deve exibir 'Login efetuado com sucesso' ao realizar login com sucesso")
    void shouldDisplaySuccessToastOnSuccessfulLogin() {
        driver.get(DEFAULT_URL+"/login");

        driver.findElement(By.id("email")).sendKeys("luigi@gmail.com");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();

        boolean correctText = text.equals("Login efetuado com sucesso");
        boolean correctUrl = driver.getCurrentUrl().equals(DEFAULT_URL+"/");

        Assertions.assertThat(correctText && correctUrl).isTrue();
    }

    @Test
    @DisplayName("Deve mostrar 'Preencha o e-mail e senha antes de entrar' ao deixar campos obrigatórios vazios")
    void shouldShowValidationMessageWhenFieldsAreLeftEmpty() {
        driver.get(DEFAULT_URL+"/login");

        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("Preencha o e-mail e senha antes de entrar");
    }

    @Test
    @DisplayName("Deve mostrar 'Credenciais inválidas' ao inserir login incorreto")
    void shouldShowInvalidCredentialsMessageWhenLoginFails() {
        driver.get(DEFAULT_URL+"/login");

        driver.findElement(By.id("email")).sendKeys("joao@email.com");
        driver.findElement(By.id("password")).sendKeys("senhaerrada");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("Credenciais inválidas");
    }
}
