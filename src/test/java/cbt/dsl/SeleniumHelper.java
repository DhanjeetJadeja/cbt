package cbt.dsl;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class SeleniumHelper {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public SeleniumHelper() {
        // declaration and instantiation of objects/variables
//        System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/chromedriver");
//        driver = new FirefoxDriver();

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void close() {
        driver.close();
        driver.quit();
    }

    public void navitgateTo(String url) {
        driver.get(url);
        dumpSource();
    }

    public int scrape() {
        int count = 0;
        String[] tags = {"a", "input", "button"};
        for (String tag : tags) {
            String cssSelector = "div " + tag;
            List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
            for (WebElement element : elements) {
                System.out.println(cssSelector + "\t" + element.getText());
            }
            count = count + elements.size();
        }
        return count;
    }

    public int products() {
        String gridSelector = "#product_grid";
        String itemSelector = ".grid_item";
        WebElement grid = driver.findElement(By.cssSelector(gridSelector));
        List<WebElement> elements = grid.findElements(By.cssSelector(itemSelector));
        return elements.size();
    }

    public String getItemsInCart() {
        return getTextForElement(".cart_bt");
    }

    public String getAccountName() {
        return getTextForElement(".access_link");
    }

    private String getTextForElement(String locator) {
        return driver.findElement(By.cssSelector(locator)).getText();
    }

    private void clickOn(String locator) {
        WebElement element = driver.findElement(By.cssSelector(locator));
        if (element.isEnabled())
            element.click();
    }

    private void type(String text, String locator) {
        WebElement input = driver.findElement(By.cssSelector(locator));
        input.clear();
        input.sendKeys(text);
    }

    private boolean isAvailable(String locator) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            driver.findElement(By.cssSelector(locator));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element " + locator + " is not available.");
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return false;
    }

    private boolean isAvailable(String locator, WebElement webElement) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            webElement.findElement(By.cssSelector(locator));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element " + locator + " is not available in " + webElement);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return false;
    }

    private boolean isDisabled(String locator) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebElement webElement = driver.findElement(By.cssSelector(locator));
            String disabled = webElement.getAttribute("disabled");
            if (null != disabled && disabled.equalsIgnoreCase("true")) {
                return true;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element " + locator + " is not available");
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return false;
    }

    public int search(String text) {
        String searchInputLocator = ".custom-search-input input";
        if (isAvailable(searchInputLocator)) {
            type(text, searchInputLocator);
            clickOn(".header-icon_search_custom");

        } else {
            String searchGlassLocator = ".btn_search_mob";
            clickOn(searchGlassLocator);

            String searchMop = ".search_mop_wp";
            type(text, searchMop);

            String searchButton = "input[type='submit']";
            clickOn(searchButton);
        }
        return products();
    }

    public void dumpSource() {
        try {
            Files.write(Paths.get(System.currentTimeMillis() + ".html"), Collections.singleton(driver.getPageSource()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFilterOn(String type, String subtype) {
        List<WebElement> types = driver.findElements(By.cssSelector(".filter_type"));
        String typeTitle;
        boolean flag = false;
        for (WebElement we : types) {
            WebElement typeTitleElement = we.findElement(By.cssSelector("h4"));
            typeTitle = typeTitleElement.getText();
            if (type.equals(typeTitle)) {
                if (!isAvailable(".opened", typeTitleElement)) {
                    typeTitleElement.click();
                }
                if (isAvailable(".show", we)) {
                    List<WebElement> subtypes = we.findElements(By.cssSelector("li"));
                    for (WebElement st : subtypes) {
                        String label = st.findElement(By.cssSelector("label")).getText();
                        if (label.trim().toLowerCase().contains(subtype.toLowerCase())) {
                            WebElement span = st.findElement(By.tagName("span"));
                            if (span.getAttribute("class").equalsIgnoreCase("checkmark")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public int filterOn(String type, String subtype) {
        if (isFilterOn(type, subtype) && !isDisabled(".buttons #resetBtn")) {
            clickOn(".buttons #resetBtn");
        }
        List<WebElement> types = driver.findElements(By.cssSelector(".filter_type"));
        String typeTitle;
        boolean flag = false;
        for (WebElement we : types) {
            WebElement typeTitleElement = we.findElement(By.cssSelector("h4"));
            typeTitle = typeTitleElement.getText();
            if (type.equals(typeTitle)) {
                if (!isAvailable(".opened", typeTitleElement)) {
                    typeTitleElement.click();
                }
                if (isAvailable(".show", we)) {
                    List<WebElement> subtypes = we.findElements(By.cssSelector("li"));
                    for (WebElement st : subtypes) {
                        String label = st.findElement(By.cssSelector("label")).getText();
                        if (label.trim().toLowerCase().contains(subtype.toLowerCase())) {
                            WebElement span = st.findElement(By.tagName("span"));
                            span.click();
                            if (span.getAttribute("class").equalsIgnoreCase("checkmark")) {
                                flag = true;
                            }
                        }
                    }
                }
            }
        }
        if (isFilterOn(type, subtype) && !isDisabled(".buttons #filterBtn")) {
            clickOn(".buttons #filterBtn");
            return products();
        }
        return -1;
    }

    public String listAppliedFilters() {
        StringBuilder sb = new StringBuilder();
        for (String type : getTypeOfFilters()) {
            for (String subType : getSubTypeOfFilters(type, true)) {
                sb.append(type + "\t" + subType + "\n");
            }
        }
        return sb.toString();
    }

    public Map<String, String> getAllFiltersMap() {
        Map<String, String> retVal = new LinkedHashMap<>();
        for (String type : getTypeOfFilters()) {
            for (String subType : getSubTypeOfFilters(type)) {
                retVal.put(subType, type);
            }
        }
        return retVal;
    }

    public void turnFiltersOn() {

    }

    private List<String> getTypeOfFilters() {
        List<String> retVal = new ArrayList<>();
        List<WebElement> types = driver.findElements(By.cssSelector(".filter_type"));
        for (WebElement type : types) {
            WebElement typeTitleElement = type.findElement(By.cssSelector("h4"));
            retVal.add(typeTitleElement.getText());
        }
        return retVal;
    }

    private List<String> getSubTypeOfFilters(String type) {
        List<String> retVal = new ArrayList<>();
        List<WebElement> types = driver.findElements(By.cssSelector(".filter_type"));
        for (WebElement we : types) {
            WebElement typeTitleElement = we.findElement(By.cssSelector("h4"));
            String typeTitle = typeTitleElement.getText();
            if (type.equals(typeTitle)) {
                if (!isAvailable(".opened", typeTitleElement)) {
                    typeTitleElement.click();
                }
                if (isAvailable(".show", we)) {
                    List<WebElement> subtypes = we.findElements(By.cssSelector("li"));
                    for (WebElement st : subtypes) {
                        String label = st.findElement(By.cssSelector("label")).getText();
                        label = label.substring(0, label.indexOf("\n"));
                        retVal.add(label);
                    }
                }
            }
        }
        return retVal;
    }

    private List<String> getSubTypeOfFilters(String type, boolean checkedOnly) {
        List<String> retVal = new ArrayList<>();
        List<WebElement> types = driver.findElements(By.cssSelector(".filter_type"));
        for (WebElement we : types) {
            WebElement typeTitleElement = we.findElement(By.cssSelector("h4"));
            String typeTitle = typeTitleElement.getText();
            if (type.equals(typeTitle)) {
                if (!isAvailable(".opened", typeTitleElement)) {
                    typeTitleElement.click();
                }
                if (isAvailable(".show", we)) {
                    List<WebElement> subtypes = we.findElements(By.cssSelector("li"));
                    for (WebElement st : subtypes) {
                        String label = st.findElement(By.cssSelector("label")).getText();
                        label = label.substring(0, label.indexOf("\n"));
                        WebElement span = st.findElement(By.tagName("span"));
                        if (checkedOnly) {
                            boolean isChecked = span.getAttribute("class").equalsIgnoreCase("checkmark");
                            if (isChecked) {
                                retVal.add(label);
                            }
                        } else {
                            retVal.add(label);
                        }
                    }
                }
            }
        }
        return retVal;
    }
}