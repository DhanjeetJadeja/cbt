package cbt.dsl;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.Browser;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.remote.Browser.CHROME;
import static org.openqa.selenium.remote.Browser.FIREFOX;

public class Configuration
{
    static List<BrowserConfig> configs = new ArrayList<>();

    public Configuration()
    {
        addBrowser(1920, 1080, CHROME);
        addBrowser(1280, 960, FIREFOX);
        addBrowser(768, 700, FIREFOX);
        addDeviceEmulation("iPhoneX", ScreenOrientation.PORTRAIT);
        addDeviceEmulation("iPhoneX", ScreenOrientation.LANDSCAPE);
    }

    public static void addBrowser(int width, int height, Browser bt)
    {
        configs.add(new BrowserConfig(width, height, bt));
    }

    public void addDeviceEmulation(String deviceName, ScreenOrientation so)
    {
        if (so.equals(ScreenOrientation.PORTRAIT))
        {
            configs.add(new BrowserConfig(375, 812, CHROME));
        }
        else
        {
            configs.add(new BrowserConfig(812, 375, CHROME));
        }
    }

    public List<BrowserConfig> getAllConfigs()
    {
        return configs;
    }

    public List<BrowserConfig> getAllConfigsForBrowser(String browser)
    {
        List<BrowserConfig> retVal = new ArrayList<>();
        for (BrowserConfig config : configs)
        {
            if (config.getBrowser().equals(browser))
            {
                retVal.add(config);
            }
        }
        return retVal;
    }
}
