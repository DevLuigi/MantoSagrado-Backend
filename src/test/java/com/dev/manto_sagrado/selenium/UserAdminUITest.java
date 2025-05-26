package com.dev.manto_sagrado.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

@ActiveProfiles("test")
public class UserAdminUITest {
    private final static String DEFAULT_URL = "http://localhost:3000/admin";

    static WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Deve exibir 'Login efetuado com sucesso' ao realizar login com sucesso")
    void shouldDisplaySuccessToastOnSuccessfulLogin() {
        driver.get(DEFAULT_URL+"/login");

        driver.findElement(By.id("email")).sendKeys("admin@gmail.com");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();

        boolean correctText = text.equals("Login efetuado com sucesso");
        boolean correctUrl = driver.getCurrentUrl().equals(DEFAULT_URL+"/menu");

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

    @Test
    @DisplayName("Deve exibir 'O e-mail informado já está em uso. Por favor, utilize outro' ao cadastrar e-mail já existente")
    void shouldDisplayEmailAlreadyInUseMessageWhenRegisteringUserWithExistingEmail() {
        driver.get(DEFAULT_URL+"/user/register");

        driver.findElement(By.id("name")).sendKeys("Luigi");
        driver.findElement(By.id("cpf")).sendKeys("89489675091");
        driver.findElement(By.id("email")).sendKeys("luigidasilvacoelho@gmail.com");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("a");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("O e-mail informado já está em uso. Por favor, utilize outro");
    }

    @Test
    @DisplayName("Deve exibir 'CPF inválido. Por favor, verifique os dígitos' ao inserir usuário com cpf inválido")
    void shouldDisplayInvalidCpfWhenInsertUserWithInvalidCpf() {
        driver.get(DEFAULT_URL+"/user/register");

        driver.findElement(By.id("name")).sendKeys("Luigi");
        driver.findElement(By.id("cpf")).sendKeys("12345678900");
        driver.findElement(By.id("email")).sendKeys("random951@gmail.com");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("a");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("CPF inválido. Por favor, verifique os dígitos");
    }

    @Test
    @DisplayName("Deve exibir 'Registro inserido com sucesso!' ao inserir usuário válido")
    void shouldDisplayRegisterInsertedWithSuccessWhenInsertAValidUser() {
        driver.get(DEFAULT_URL+"/user/register");

        driver.findElement(By.id("name")).sendKeys("Luigi");
        driver.findElement(By.id("cpf")).sendKeys("89489675091");
        driver.findElement(By.id("email")).sendKeys("emailinsercao@gmail.com");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("a");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();

        boolean correctText = text.equals("Registro inserido com sucesso!");
        boolean correctUrl = driver.getCurrentUrl().equals(DEFAULT_URL+"/user/management");
        System.out.println(driver.getCurrentUrl());

        Assertions.assertThat(correctText && correctUrl).isEqualTo(true);
    }
}
