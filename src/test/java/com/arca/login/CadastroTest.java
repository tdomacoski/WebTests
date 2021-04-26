package com.arca.login;

import com.arca.util.StringUtils;
import com.arca.util.ThreadUtils;
import com.arca.util.URLS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CadastroTest {

    private final String NOME = "customer_name";
    private final String EMAIL = "customer_email";
    private final String SENHA = "customer_password";
    private final String CONFIRME_SENHA = "customer_password_confirmation";
    private final String NASCIMENTO = "customer_birthdate";
    private final String TELEFONE = "customer_phone";
    private final String CPF = "customer_cpf";
    private final String CIDADE = "city";
    private final String ESTADO = "state";
    private final String LOGRADOURO = "street";
    private final String NUMERO = "number";
    private final String BAIRRO = "neighborhood";
    private final String COMPLEMENTO = "complement";

    private WebDriver webDriver;



    @Before
    public void init(){
        webDriver = new InternetExplorerDriver();
        webDriver = new ChromeDriver();
//            webDriver = WebDriverUtils.getChromeDriver();
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @After
    public void finish(){
        ThreadUtils.sleep(8000);
        if(null != webDriver){
            webDriver.close();
        }
    }
    @Test
    public void validarNomeVazio(){
        webDriver.get(URLS.getCadastro(true));
        final WebElement inputNome = webDriver.findElement(By.id(NOME));
        assertNotNull(inputNome);
        inputNome.sendKeys(" ");
        ThreadUtils.sleep(100);



    }
    @Test
    public void validarEmailInvalido(){
        webDriver.get(URLS.getCadastro(true));

        final WebElement inputEmail = webDriver.findElement(By.id(EMAIL));
        assertNotNull(inputEmail);
        inputEmail.sendKeys(StringUtils.generateString(10));
        webDriver.findElement(By.name("commit")).click();
        ThreadUtils.sleep(100);

        /** Vamos pegar o card do email, e verificar se possui um elemento de erro */
        final WebElement cardEmail = webDriver.findElement(By.cssSelector("div[class='input email required customer_email field_with_errors']"));
        assertNotNull(cardEmail);

        final WebElement error = cardEmail.findElement(By.className("error"));
        assertNotNull(error);
        assertEquals("não é válido", error.getText());
    }
}
