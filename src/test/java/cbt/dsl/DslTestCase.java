package cbt.dsl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DslTestCase {
    private static SeleniumHelper browser;

    public DslTestCase() {
        browser = new SeleniumHelper();
    }

    public static SeleniumHelper getBrowser() {
        return browser;
    }
}
