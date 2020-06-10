package com.arca.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverUtils {
    public static final String CHROME_DRIVER = "C:\\chromeWebDriver\\chromedriver.exe";

    private static WebDriver chromeDriver;
    public static final WebDriver getChromeDriver(){
        if(null == chromeDriver){
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
            chromeDriver = new ChromeDriver();
        }
        return chromeDriver;
    }



}
