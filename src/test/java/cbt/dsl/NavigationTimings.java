package cbt.dsl;

import java.util.HashMap;
import java.util.Map;

public class NavigationTimings {
    private String url;
    private Map<String, Long> longs = new HashMap<>();

    public NavigationTimings(String url, String browserResponse) {
        this.url = url;
        if (browserResponse.startsWith("{")) {
            browserResponse = browserResponse.substring(1);
        }
        if (browserResponse.endsWith("}")) {
            browserResponse = browserResponse.substring(0, browserResponse.length() - 1);
        }
        String[] timings = browserResponse.split(",");
        for (String row : timings) {
            if (row.contains("=")) {
                String[] columns = row.split("=");
                String key = columns[0] == null ? "" : columns[0].trim();
                String value = columns[1] == null ? "" : columns[1].trim();
                try {
                    longs.put(key, Long.parseLong(value));
                } catch (NumberFormatException e) {
                    System.out.println(String.format("NavigationTimings could not be created from %s - %s.", row, columns));
                }
            } else {
                System.out.println(String.format("NavigationTimings could not be created from %s.", browserResponse));
            }
        }
    }

    @Override
    public String toString() {
        return "NavigationTimings{" +
                "url='" + url + '\'' +
                ", longs=" + longs +
                '}';
    }

    public Long getLoadTimeInMillis() {
        return longs.get("loadEventEnd") - longs.get("navigationStart");
    }
}
