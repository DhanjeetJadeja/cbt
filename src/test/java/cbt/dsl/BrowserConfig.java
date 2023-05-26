package cbt.dsl;

import org.openqa.selenium.remote.Browser;

public class BrowserConfig
{
    private final int width;
    private final int height;
    private final Browser browser;

    public BrowserConfig(int width, int height, Browser browser)
    {
        this.width = width;
        this.height = height;
        this.browser = browser;
    }

    @Override
    public String toString()
    {
        return browser.browserName() + "-" + width + "x" + height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Browser getBrowser()
    {
        return browser;
    }
}
