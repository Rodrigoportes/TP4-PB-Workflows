package br.com.infnet.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FuncionarioListPage {

    private final WebDriver driver;
    private final String BASE_URL = "http://localhost:7000/funcionarios";
    private final By LINK_NOVO_FUNCIONARIO = By.linkText("Adicionar Novo Funcionário");
    private final By TABLE_BODY = By.cssSelector("table tbody");

    public FuncionarioListPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToList() {
        driver.get(BASE_URL);
    }

    public void clickNewFuncionario() {
        driver.findElement(LINK_NOVO_FUNCIONARIO).click();
    }

    public WebElement findFuncionarioRow(String nome) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Garante que o nome esteja presente antes de tentar encontrar a linha
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//td[text()='" + nome + "']")
        ));
        // Retorna a linha inteira
        return driver.findElement(By.xpath("//td[text()='" + nome + "']/parent::tr"));
    }

    public void clickEdit(String nome) {
        WebElement row = findFuncionarioRow(nome);
        row.findElement(By.xpath(".//a[text()='Editar']")).click();

        // Espera a URL mudar para a página de edição
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/edit/"));
    }

    public void deleteFuncionario(String nome) {
        WebElement row = findFuncionarioRow(nome);
        // Encontra o botão Deletar dentro do formulário POST e clica
        row.findElement(By.cssSelector("form button[type='submit']")).click();

        // Espera o redirecionamento de volta para a lista
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(BASE_URL));
    }

    public String getTableText() {
        return driver.findElement(TABLE_BODY).getText();
    }

    public boolean isFuncionarioPresent(String nome, String cargo) {
        try {
            return driver.findElement(By.xpath("//tr/td[text()='" + nome + "']/following-sibling::td[text()='" + cargo + "']")).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}