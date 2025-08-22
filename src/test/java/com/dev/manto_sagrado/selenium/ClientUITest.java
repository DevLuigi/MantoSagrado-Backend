package com.dev.manto_sagrado.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@ActiveProfiles("test")
public class ClientUITest {
    private final static String DEFAULT_URL = "http://localhost:3000";

    WebDriver driver;

    @BeforeEach
    void setup() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        Path tempProfile = Files.createTempDirectory("selenium-profile-");
        options.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath().toString());

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
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

    @Test
    @DisplayName("Deve registrar um novo cliente e redirecionar para a tela de cadastro de endereço")
    void shouldRegisterNewClientAndRedirectToAddressRegistrationScreen() {
        driver.get(DEFAULT_URL+"/register");

        driver.findElement(By.id("name")).sendKeys("conta de teste");
        driver.findElement(By.id("cpf")).sendKeys("02304240070");
        driver.findElement(By.id("email")).sendKeys("emailinsercao@email.com");
        driver.findElement(By.id("birthDate")).sendKeys("26042004");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("m");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        boolean correctText = text.equals("Registro inserido com sucesso!");
        boolean correctUrl = driver.getCurrentUrl().equals(DEFAULT_URL+"/address/list");

        Assertions.assertThat(correctText && correctUrl).isTrue();
    }

    @Test
    @DisplayName("Deve exibir 'Preencha todos os campos!' quando o cliente deixar campos obrigatórios em branco")
    void shouldDisplayErrorMessageWhenRequiredFieldsAreEmpty() {
        driver.get(DEFAULT_URL+"/register");

        driver.findElement(By.id("submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("Preencha todos os campos!");
    }

    @Test
    @DisplayName("Deve exibir 'As senhas não são iguais!' quando o usuário inserir senhas diferentes")
    void shouldDisplayErrorMessageWhenPasswordsDoNotMatch() {
        driver.get(DEFAULT_URL+"/register");

        driver.findElement(By.id("name")).sendKeys("conta de teste");
        driver.findElement(By.id("cpf")).sendKeys("02304240070");
        driver.findElement(By.id("email")).sendKeys("emaildeteste@email.com");
        driver.findElement(By.id("birthDate")).sendKeys("26042004");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("m");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("As senhas não são iguais!");
    }

    @Test
    @DisplayName("Deve exibir 'CPF inválido. Por favor, verifique os dígitos' ao inserir cliente com cpf inválido")
    void shouldDisplayInvalidCpfWhenInsertClientWithInvalidCpf() {
        driver.get(DEFAULT_URL+"/register");

        driver.findElement(By.id("name")).sendKeys("conta de teste");
        driver.findElement(By.id("cpf")).sendKeys("12345678900");
        driver.findElement(By.id("email")).sendKeys("emailcpfinvalido@email.com");
        driver.findElement(By.id("birthDate")).sendKeys("26042004");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("m");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("CPF inválido. Por favor, verifique os dígitos");
    }

    @Test
    @DisplayName("Deve exibir 'O e-mail informado já está em uso. Por favor, utilize outro' ao cadastrar e-mail já existente")
    void shouldDisplayEmailAlreadyInUseMessageWhenRegisteringUserWithExistingEmail() {
        driver.get(DEFAULT_URL+"/register");

        driver.findElement(By.id("name")).sendKeys("conta de teste");
        driver.findElement(By.id("cpf")).sendKeys("12345678900");
        driver.findElement(By.id("email")).sendKeys("emaildeteste@email.com");
        driver.findElement(By.id("birthDate")).sendKeys("26042004");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("confirmPassword")).sendKeys("123");
        driver.findElement(By.id("select")).sendKeys("m");
        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Toastify__toast")
        ));

        String text = toast.getText();
        Assertions.assertThat(text).isEqualTo("O e-mail informado já está em uso. Por favor, utilize outro");
    }
}
