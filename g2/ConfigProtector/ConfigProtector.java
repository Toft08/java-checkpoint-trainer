import java.util.List;

public class ConfigProtector {
    public String hideSensitiveData(String configFile, List<String> sensitiveKeys) {
        String[] lines = configFile.split("\n");

        if (configFile.isEmpty()) {
            return "";
        }
        
        for (int i = 0; i < lines.length; i++) {
            for (String key : sensitiveKeys) {
                if (lines[i].startsWith(key + "=")) {
                    String[] parts = lines[i].split("=", 2);
                    String value = parts[1];

                    String masked = "*".repeat(value.length());

                    lines[i] = key + "=" + masked;
                }
            }
        }
        return String.join("\n", lines) + "\n";
    }
}