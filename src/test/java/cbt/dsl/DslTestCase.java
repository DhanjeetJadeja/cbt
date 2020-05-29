package cbt.dsl;

public class DslTestCase {
    private static SeleniumHelper browser;

    public DslTestCase() {
        browser = new SeleniumHelper();
    }

    public static SeleniumHelper getBrowser() {
        return browser;
    }

    public void setupBrowserConfig(TestConfig config) {
        browser.init(config);
    }

    public void log(TestConfig config, int task, String testName, String domId, boolean comparisonResult) {
        System.out.println("Task: " + task + ", Test Name: " + testName + ", DOM Id: " + domId + ", Browser: " + config.getBrowser()
                + ", Viewport: " + config.getWidth() + "x" + config.getHeight() + ", Device: " + config.getBrowser() + ", Status: " + (comparisonResult ? "Pass" : "Fail"));
    }
}
