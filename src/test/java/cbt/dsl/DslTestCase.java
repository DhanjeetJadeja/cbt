package cbt.dsl;

public class DslTestCase
{
    private static SeleniumHelper browser;

    public DslTestCase()
    {
        browser = new SeleniumHelper();
    }

    public static SeleniumHelper getBrowser()
    {
        return browser;
    }

    public void log(BrowserConfig config, int task, String testName, String domId, boolean comparisonResult)
    {
        System.out.println("Task: " + task +
                ", Test Name: " + testName +
                ", DOM Id: " + domId +
                ", Browser: " + config.getBrowser().browserName() +
                ", Viewport: " + config.getWidth() + "x" + config.getHeight() +
                ", Device: " + config.getBrowser().browserName() +
                ", Status: " + (comparisonResult ? "Pass" : "Fail"));
    }
}
