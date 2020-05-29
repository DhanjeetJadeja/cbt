package cbt.dsl;

public class TestConfig {
    private final int width;
    private final int height;
    private final String bt;

    public TestConfig(int width, int height, String bt) {
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

    public String getBrowser() {
        return bt;
    }
}
