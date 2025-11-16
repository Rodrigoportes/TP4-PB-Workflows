package br.com.infnet.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FuncionarioFormPage {

    private final WebDriver driver;
    private final By FIELD_NOME = By.id("nome");
    private final By FIELD_CARGO = By.id("cargo");
    private final By FORM = By.cssSelector("form");
    private final By BUTTON_SUBMIT = By.cssSelector("form button[type='submit']");
    private final String BASE_URL = "http://localhost:7000/funcionarios";

    public FuncionarioFormPage(WebDriver driver) {
        this.driver = driver;
    }

    // Preenche os campos Nome e Cargo no formulário.

    public void fillForm(String nome, String cargo) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Espera garantir que o formulário foi carregado antes de preencher
        wait.until(ExpectedConditions.presenceOfElementLocated(FIELD_NOME));

        driver.findElement(FIELD_NOME).clear();
        driver.findElement(FIELD_NOME).sendKeys(nome);

        driver.findElement(FIELD_CARGO).clear();
        driver.findElement(FIELD_CARGO).sendKeys(cargo);
    }

    //Submete o formulário e espera o redirecionamento de sucesso para a lista.

    public void submitForm() {
        driver.findElement(FORM).submit(); // Submissão do formulário

        // Espera o redirecionamento de volta para a lista (condição de sucesso)
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(BASE_URL));
    }

    //Apenas clica no botão de submissão.

    public void clickSubmitButton() {
        driver.findElement(BUTTON_SUBMIT).click();
    }
}