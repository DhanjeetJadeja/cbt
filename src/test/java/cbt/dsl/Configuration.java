package cbt.dsl;

import org.openqa.selenium.ScreenOrientation;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.remote.BrowserType.CHROME;

public class Configuration {
    static List<TestConfig> configs = new ArrayList<>();

    public Configuration() {
        addBrowser(1200, 700, CHROME);
//        addBrowser(1200, 700, FIREFOX);
//        addBrowser(1200, 700, EDGE);
//        addBrowser(768, 700, CHROME);
//        addBrowser(768, 700, FIREFOX);
//        addBrowser(768, 700, EDGE);
//        addDeviceEmulation("iPhoneX", ScreenOrientation.PORTRAIT);
    }

    public static void addBrowser(int width, int height, String bt) {
        configs.add(new TestConfig(width, height, bt));
    }

    public void addDeviceEmulation(String deviceName, ScreenOrientation so) {
        if (so.equals(ScreenOrientation.PORTRAIT)) {
            configs.add(new TestConfig(375, 812, CHROME));
        } else {
            configs.add(new TestConfig(812, 375, CHROME));
        }
    }

    public List<TestConfig> getAllConfigs() {
        return configs;
    }

    public List<TestConfig> getAllConfigsForBrowser(String browser) {
        List<TestConfig> retVal = new ArrayList<>();
        for (TestConfig config : configs) {
            if (config.getBrowser().equalsIgnoreCase(browser)) {
                retVal.add(config);
            }
        }
        return retVal;
    }
}
