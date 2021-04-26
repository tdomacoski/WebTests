package com.arca;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MainTest {
    public static final String CHROME_DRIVER = "C:\\chromeWebDriver\\chromedriver.exe";

    public static void delay(long mili){
        try {
            Thread.sleep(mili);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void testPesquisa(){

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        WebDriver navegador = new ChromeDriver();
        navegador.get("https://embarcai-sales-staging.herokuapp.com/");

        /**
         * Seleção do campo Origem
         */
        final WebElement routeOrigin = navegador.findElement(By.className("route_origin_text"));
        routeOrigin.findElement(By.id("origin_text")).sendKeys("Curi");
        delay(1000);

        final WebElement locationsListOrigin = routeOrigin.findElement(By.id("locations_list"));
        final List<WebElement> resultListOrigin = locationsListOrigin.findElements(By.className("autoComplete_result"));

        assertNotNull(resultListOrigin);
        assertTrue(resultListOrigin.size() > 0 );

        resultListOrigin.get(0).click();


        /**
         * Seleção do campo destino
         */

        final WebElement routeDestination = navegador.findElement(By.className("route_destination_text"));
        routeDestination.findElement(By.id("destination_text")).sendKeys("Pont");
        delay(1000);

        final WebElement locationsListDestination = routeDestination.findElement(By.id("locations_list"));
        final List<WebElement> resultListDestination = locationsListDestination.findElements(By.className("autoComplete_result"));

        assertNotNull(resultListDestination);
        assertTrue(resultListDestination.size() > 0 );
        resultListDestination.get(0).click();
        delay(1000);


        /**
         * Seleção da data
         */
        navegador.findElement(By.id("route_departure_at")).click();

        delay(1000);
        final WebElement datepicker =navegador.findElement(By.className("datepicker-days"));
        assertNotNull(datepicker);

        final List<WebElement> days = datepicker.findElements(By.className("day"));
        // pegamos a data atual e adiciona um dia a mais
        final String changeDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1);
        for(final WebElement day :  days){
            if(day.getText().equals(changeDay)){
                if(day.isEnabled()){
                    day.click();
                    break;
                }
            }
        }

        /**
         * Clica na busca
         */
        navegador.findElement(By.id("search-trips-btn")).click();

        delay(1000);

        /**
         * Vamos pegar o calendario
         */
        final WebElement calendar = navegador.findElement(By.className("calendar"));


        delay(9000);



        navegador.close();
    }
}
