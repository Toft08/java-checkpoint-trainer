import java.util.*;
import java.net.*;

public class BreakdownURL {
    public Map<String, String> parseURL(String url) {
        Map<String, String> components = new HashMap<>();
        try {
            URL parsed = new URL(url);

            if (!parsed.getProtocol().isEmpty()) {
                components.put("protocol", parsed.getProtocol());
            }
            if (!parsed.getHost().isEmpty()) {
                components.put("domain", parsed.getHost());
            }
            if (parsed.getPort() != -1) {
                components.put("port", String.valueOf(parsed.getPort()));
            }
            if (!parsed.getPath().isEmpty()) {
                components.put("path", parsed.getPath());
            } else {
                components.put("path", "/"); // Probably not needed if you run newer version of JDK
            }
            if (!parsed.getQuery().isEmpty()) {
                components.put("query", parsed.getQuery());
            }
        } catch (Exception e) {
            return components;
        }
        return components;
    }
}