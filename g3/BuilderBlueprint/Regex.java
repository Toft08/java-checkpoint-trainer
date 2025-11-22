import java.util.List;
import java.util.ArrayList;

public class Regex {
    private StringBuilder pattern;

    public Regex() {
        pattern = new StringBuilder();
    }
    public Regex(List<String> component) {
        pattern = new StringBuilder();
        for(String c : component) {
            pattern.append(c);
        }
    }

    public String getPattern() {
        return pattern.toString();
    }

    @Override
    public String toString() {
    return getPattern();
    }
}