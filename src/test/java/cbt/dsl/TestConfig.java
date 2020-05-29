package cbt.dsl;

import org.openqa.selenium.remote.BrowserType;

public class TestConfig {
    private final int width;
    private final int height;
    private final BrowserType bt;

    public TestConfig(int width, int height, BrowserType bt) {
        this.width = width;
        this.height = height;
        this.bt = bt;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BrowserType getBt() {
        return bt;
    }
}
